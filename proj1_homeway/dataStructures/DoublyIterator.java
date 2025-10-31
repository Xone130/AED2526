package dataStructures;
import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of Two Way Iterator for DLList 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */
class DoublyIterator<E> implements Iterator<E> {
    /**
     * Node with the first element in the iteration.
     */
    private final DoublyListNode<E> firstNode;

    /**
     * Node with the next element in the iteration.
     */
    DoublyListNode<E> nextToReturn;


    /**
     * DoublyIterator constructor
     *
     * @param first - Node with the first element of the iteration
     */
    public DoublyIterator(DoublyListNode<E> first) {
        this.firstNode = first;
        this.nextToReturn = first;
    }
    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    @Override
    public E next(){
        if(!this.hasNext()) throw new NoSuchElementException();

        E element = nextToReturn.getElement();
        nextToReturn = nextToReturn.getNext();

        return element;
    }

    /**
     * Restart the iterator
     */
    @Override
    public void rewind() {
        nextToReturn = firstNode;
    }
    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     * @return true iff the iteration has more elements
     */
    @Override
    public boolean hasNext( ) {
        return nextToReturn != null;
    }


}
