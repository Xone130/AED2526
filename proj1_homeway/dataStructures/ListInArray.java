package dataStructures;
import dataStructures.exceptions.*;
/**
 * List in Array
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class ListInArray<E> implements List<E> {

    private static final int FACTOR = 2;
    /**
     * Array of generic elements E.
     */
    private E[] elems;

    /**
     * Number of elements in array.
     */
    private int counter;


    /**
     * Construtor with capacity.
     * @param dimension - initial capacity of array.
     */
    @SuppressWarnings("unchecked")
    public ListInArray(int dimension) {
        elems = (E[]) new Object[dimension];
        counter = 0;
    }
    /**
     * Returns true iff the list contains no elements.
     *
     * @return true if list is empty
     */
    @Override
    public boolean isEmpty() {
        return counter == 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return number of elements in the list
     */
    @Override
    public int size() {
        return counter;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     *
     * @return Iterator of the elements in the list
     */
    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<>(elems,counter);
    }

    /**
     * Returns the first element of the list.
     *
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    @Override
    public E getFirst() {
        if(this.isEmpty()) throw new NoSuchElementException();
        return elems[0];
    }

    /**
     * Returns the last element of the list.
     *
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    @Override
    public E getLast() {
        if(this.isEmpty()) throw new NoSuchElementException();
        return elems[counter - 1];
    }

    /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     *
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     */
    @Override
    public E get(int position) {
        if(!isPositionValid(position)) throw new InvalidPositionException();
        if(position == 0) return this.getFirst();
        if(position == counter - 1) return this.getLast();
        
        return elems[position];
    }

    /**
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     *
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     */
    @Override
    public int indexOf(E element) { // assumes element != null
        Iterator<E> it = this.iterator();

        int index = 0;
        while(it.hasNext()){
            E current = it.next();

            if(current != null){ // assumes elements might be null in the middle of the list
                if(current.equals(element)) return index;
            }
            index++;
        }

        return -1;
    }

    /**
     * Inserts the specified element at the first position in the list.
     *
     * @param element to be inserted
     */
    @Override
    public void addFirst(E element) {
        
        if(this.isFull()) this.extendArray();
        for(int i = counter - 1; i >= 0; i--) elems[i + 1] = elems[i]; // shift
        
        elems[0] = element;
        counter++;
    }

    /**
     * Inserts the specified element at the last position in the list.
     *
     * @param element to be inserted
     */
    @Override
    public void addLast(E element) {
        if(this.isFull()) this.extendArray();
        
        elems[counter] = element;
        counter++;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     *
     * @param position - position where to insert element
     * @param element  - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     */
    @Override
    public void add(int position, E element) {
        if(!isPositionValidForAdd(position)) throw new InvalidPositionException();
        if(elems == null || elems.length == 0) this.extendArray();
        if(this.isFull()) this.extendArray();

        if(position == 0) this.addFirst(element);
        else if(position == counter) this.addLast(element);
        else{
            for(int i = counter - 1; i >= position; i--) elems[i + 1] = elems[i]; //shift until position

            elems[position] = element;
            counter++;
        }
    }

    /**
     * Removes and returns the element at the first position in the list.
     *
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    @Override
    public E removeFirst() {
        if(this.isEmpty()) throw new NoSuchElementException();
        E removed = elems[0];

        for(int i = 1; i < counter; i++) elems[i - 1] = elems[i]; // shift

        elems[counter - 1] = null;
        counter--;
        return removed;
    }

    /**
     * Removes and returns the element at the last position in the list.
     *
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    @Override
    public E removeLast() {
        if(this.isEmpty()) throw new NoSuchElementException();
        E removed = elems[counter - 1];

        elems[counter - 1] = null;
        counter--;
        return removed;
    }

    /**
     * Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     *
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     */
    @Override
    public E remove(int position) {
        if(!this.isPositionValid(position)) throw new InvalidPositionException();
        if(position == 0) return this.removeFirst();
        if(position == counter - 1) return this.removeLast();
        E removed = elems[position];

        for(int i = position + 1; i < counter; i++) elems[i - 1] = elems[i]; //shift left untill position;

        elems[counter - 1] = null;
        counter--;
        return removed;
    }

    // ============== helpers ==============


    private boolean isPositionValid(int position){
        return position >= 0 && position <= counter - 1;
    }

        private boolean isPositionValidForAdd(int position){
        return position >= 0 && position <= counter;
    }

    /**
     * 
     * @return true if the array is full
     */
    private boolean isFull(){
        return counter == elems.length;
    }

    /**
     * extends the array using FACTOR
     */
    @SuppressWarnings("unchecked")
    private void extendArray(){
        int newLength = Math.max(1, elems.length * FACTOR);

        E[] newElems = (E[]) new Object[newLength];

        System.arraycopy(elems, 0, newElems, 0, counter);
        elems = newElems;
    }
}
