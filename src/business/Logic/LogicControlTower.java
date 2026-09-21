package business.Logic;

import data.DoubleCircleListFlight;
import data.SimpleListOccupancy;
import data.SimpleListReservation;
import domain.Flight;
import domain.Node.NodeDoubleList;
import domain.Node.NodeSimpleList;

public class LogicControlTower {

	private DoubleCircleListFlight flight;
	private SimpleListReservation listRservation;
	private SimpleListOccupancy listOccupancy;

	public LogicControlTower(DoubleCircleListFlight flight) {
		this.flight = flight;
		this.listRservation = new SimpleListReservation();
		this.listOccupancy = new SimpleListOccupancy();
	}

	// Metodo para calcular el porcentaje de ocupacion de cada vuelo
	public void calculateOccupancyRates(SimpleListReservation listReservation) {

		if (this.flight.isEmpty()) {
			System.out.println("No hay vuelos registrados");
			return;
		}

		NodeDoubleList<Flight> aux = this.flight.getFirtsNodeCircleDoubleList();

		do {

			Flight currentFlight = aux.getData();
			NodeSimpleList<LogicReservation> currentReservation = listReservation.getFirstReservation();
			boolean found = false;

			while (currentReservation != null) {

				LogicReservation reservation = currentReservation.getData();

				if (currentFlight.getNumberFlight() == reservation.getFlight().getNumberFlight()) {

					int capacity = currentFlight.getMaximumCapacity();
					int passengers = reservation.getQuantityPassengersByFlight();
					double occupancyRate = ((double) passengers / capacity) * 100;

					listOccupancy.addLastSimpleListOccupancy(currentFlight.getNumberFlight(), occupancyRate);
					found = true;
					break;
				}

				currentReservation = currentReservation.getNextNode();
			}

			if (!found) {
				listOccupancy.addLastSimpleListOccupancy(currentFlight.getNumberFlight(), 0.0);
			}

			aux = aux.getNextNode();
		} while (aux != this.flight.getFirtsNodeCircleDoubleList());
	}



	public void prioritizeFlights() {

		if (this.flight.isEmpty()) {
			System.out.println("No hay vuelos registrados para priorizar.");
			return;
		}

		NodeDoubleList<Flight> head = this.flight.getFirtsNodeCircleDoubleList();
		NodeDoubleList<Flight> tail = this.flight.getLastNodeCircleDoubleList();

		// 1. "Abrir" la lista: se rompe el enlace circular para que el
		//    QuickSort la vea como una lista doble normal, terminada en null.
		//    (si hay un solo vuelo, head y tail son el mismo nodo, y estas
		//    dos líneas igual lo dejan con next=null y previus=null, sin
		//    necesitar un caso aparte)
		tail.setNextNode(null);
		head.setPreviusNode(null);

		// 2. Ordenar (esto no cambia: es el mismo método que ya tenías)
		NodeDoubleList<Flight> newHead = orderingFlighByOcuppationQuickSort(head, listOccupancy);

		// 3. Encontrar la nueva cola caminando hasta el final
		NodeDoubleList<Flight> newTail = newHead;
		while (newTail.getNextNode() != null) {
			newTail = newTail.getNextNode();
		}

		// 4. "Cerrar" la lista de nuevo en círculo
		newTail.setNextNode(newHead);
		newHead.setPreviusNode(newTail);

		// 5. Actualizar los punteros de la lista circular con el nuevo orden
		this.flight.setFirtsNodeCircleDoubleList(newHead);
		this.flight.setLastNodeCircleDoubleList(newTail);
		this.flight.setCurrentNode(newHead); // la navegación arranca de nuevo desde el primero
	}


	/**
	 * QuickSort manual sobre una lista doblemente enlazada de vuelos (sin arreglos
	 * ni colecciones), ordenando de MAYOR a MENOR porcentaje de ocupación. Si dos
	 * vuelos tienen la misma ocupación, gana (va primero) el de menor número de
	 * vuelo.
	 *
	 * Idea general del QuickSort aplicado a lista enlazada: 1. Elegimos un "pivote"
	 * (aquí: siempre el primer nodo de la lista). 2. Partimos el resto de los nodos
	 * en dos grupos: - "prioritize" = los que van ANTES que el pivote (más
	 * ocupación, o empate con menor número de vuelo) - "remaining" = los que van
	 * DESPUÉS que el pivote 3. Ordenamos cada grupo por separado llamando a este
	 * mismo método (recursividad = quicksort de cada mitad). 4. Pegamos todo en el
	 * orden final: [prioritize ordenado] + [pivote] + [remaining ordenado]
	 */
	public NodeDoubleList<Flight> orderingFlighByOcuppationQuickSort(NodeDoubleList<Flight> listFlight,
			SimpleListOccupancy listOccupancy) {

		// --- CASO BASE ---
		// Si la lista está vacía (null) o tiene un solo nodo (no tiene "next"),
		// ya está "ordenada" por definición: no hay nada que partir ni comparar.
		if (listFlight == null || listFlight.getNextNode() == null) {
			return listFlight;
		}

		// --- 1. ELEGIR EL PIVOTE ---
		// Se toma siempre el primer nodo de la sublista actual como pivote.
		NodeDoubleList<Flight> pivot = listFlight;

		// "allListFlight" es el resto de la lista, es decir, todo menos el pivote.
		NodeDoubleList<Flight> allListFlight = listFlight.getNextNode();

		// Se "desengancha" el pivote de la lista: se corta el enlace entre el
		// pivote y el resto, para poder tratarlo como una pieza aparte que
		// vamos a reinsertar más adelante en su posición final.
		pivot.setNextNode(null);
		if (allListFlight != null) {
			allListFlight.setPreviusNode(null);
		}

		// --- 2. PREPARAR LOS DOS GRUPOS (particiones) ---
		// "prioritize" = nodos que deben quedar ANTES del pivote en el resultado final
		NodeDoubleList<Flight> prioritizeFirst = null; // cabeza de ese grupo
		NodeDoubleList<Flight> prioritizeLast = null; // cola de ese grupo (para ir agregando al final)

		// "remaining" = nodos que deben quedar DESPUÉS del pivote
		NodeDoubleList<Flight> remainingFirst = null;
		NodeDoubleList<Flight> remainingLast = null;

		// Ocupación del vuelo que es el pivote — se calcula UNA vez, fuera del
		// bucle, para no recalcularla en cada comparación.
		double occupationPivote = listOccupancy.getOccupancyByNumberFlight(pivot.getData().getNumberFlight());

		// Puntero para recorrer el resto de la lista (todo menos el pivote)
		NodeDoubleList<Flight> current = allListFlight;

		// --- 3. RECORRER EL RESTO Y REPARTIR CADA NODO EN SU GRUPO ---
		while (current != null) {

			// Guardamos el "siguiente" ANTES de tocar los punteros de "current",
			// porque en unas líneas más abajo vamos a desconectar a "current"
			// de la lista original (y si no lo guardamos antes, perdemos el
			// camino para seguir recorriendo).
			NodeDoubleList<Flight> nextSaveNodeCircleDoubleList = current.getNextNode();

			// Se desconecta "current" de todo lo demás: va a pasar a ser el
			// último nodo de "prioritize" o de "remaining", así que sus punteros
			// viejos ya no sirven.
			current.setNextNode(null);
			current.setPreviusNode(null);

			// Ocupación del vuelo actual, para compararla contra la del pivote
			double currentOccupation = listOccupancy.getOccupancyByNumberFlight(current.getData().getNumberFlight());

			// ¿Este vuelo debe ir ANTES que el pivote en el resultado final?
			boolean before;

			if (currentOccupation != occupationPivote) {
				// Caso normal: gana el que tiene MAYOR ocupación (va primero)
				before = currentOccupation > occupationPivote;
			} else {
				// Caso de EMPATE en ocupación: desempata el número de vuelo
				// más chico (ese va primero)
				before = current.getData().getNumberFlight() < pivot.getData().getNumberFlight();
			}

			if (before) {
				// Este nodo va al grupo "prioritize" (antes del pivote)
				if (prioritizeFirst == null) {
					// Es el primer elemento que entra a ese grupo
					prioritizeFirst = current;
					prioritizeLast = current;
				} else {
					// Se agrega al final del grupo, enlazando con el último que había
					prioritizeLast.setNextNode(current);
					current.setPreviusNode(prioritizeLast);
					prioritizeLast = current;
				}

			} else {
				// Este nodo va al grupo "remaining" (después del pivote)
				if (remainingFirst == null) {
					remainingFirst = current;
					remainingLast = current;
				} else {
					remainingLast.setNextNode(current);
					current.setPreviusNode(remainingLast);
					remainingLast = current;
				}
			}

			// Avanzamos con el puntero que guardamos al principio del bucle
			// (ya que "current.getNextNode()" ahora sería null)
			current = nextSaveNodeCircleDoubleList;
		}

		// --- 4. ORDENAR RECURSIVAMENTE CADA GRUPO ---
		// Cada llamada recursiva resuelve un pedazo más chico del mismo problema,
		// hasta llegar al caso base (0 o 1 nodo).
		NodeDoubleList<Flight> prioritizeOrder = orderingFlighByOcuppationQuickSort(prioritizeFirst, listOccupancy);
		NodeDoubleList<Flight> prioritizeAll = orderingFlighByOcuppationQuickSort(remainingFirst, listOccupancy);

		// --- 5. RECONSTRUIR LA LISTA FINAL: [prioritizeOrder] + [pivot] +
		// [prioritizeAll] ---

		// Caso especial: si no había NADA en el grupo "prioritize" (nadie iba
		// antes del pivote), entonces el pivote pasa a ser la CABEZA de esta
		// sublista, seguido directo por el grupo "remaining" ya ordenado.
		if (prioritizeOrder == null) {

			pivot.setPreviusNode(null);
			pivot.setNextNode(prioritizeAll);

			if (prioritizeAll != null) {
				prioritizeAll.setPreviusNode(pivot);
			}


		}

		// Caso general: "prioritizeOrder" ya es una lista (doblemente enlazada)
		// ordenada. Hay que caminar hasta su ÚLTIMO nodo para poder pegar el
		// pivote justo después de
		return pivot; // el pivote queda como nueva cabeza
	}


	public SimpleListOccupancy getListOccupancy() {
		return listOccupancy;
	}
}