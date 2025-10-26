package dataStructures;

import dataStructures.exceptions.*;


/**
 * Sorted Doubly linked list Implementation
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */
public class SortedDoublyLinkedList<E> implements SortedList<E> {

    /**
     *  Node at the head of the list.
     */
    private DoublyListNode<E> head;
    /**
     * Node at the tail of the list.
     */
    private DoublyListNode<E> tail;
    /**
     * Number of elements in the list.
     */
    private int currentSize;
    /**
     * Comparator of elements.
     */
    private final Comparator<E> comparator;
    /**
     * Constructor of an empty sorted double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public SortedDoublyLinkedList(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * Returns true iff the list contains no elements.
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     */

    public int size() {
        return currentSize;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Returns the first element of the list.
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMin( ) {
        if(currentSize == 0) throw new NoSuchElementException();
        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMax( ) {
        if(currentSize == 0) throw new NoSuchElementException();
        return tail.getElement();
    }
    /**
     * Returns the first occurrence of the element equals to the given element in the list.
     * @return element in the list or null
     */
    public E get(E element) {
        return getEl(element);
    }

    /**
     * Returns true iff the element exists in the list.
     *
     * @param element to be found
     * @return true iff the element exists in the list.
     */
    public boolean contains(E element) {
        return getEl(element) != null;
    }

    /**
     * Inserts the specified element at the list, according to the natural order.
     * If there is an equal element, the new element is inserted after it.
     * @param element to be inserted
     */
    public void add(E element) {
        DoublyListNode<E> node = new DoublyListNode<>(element);

        if(currentSize == 0) head = tail = node;
        else{
            if(comparator.compare(element, head.getElement()) <= 0){ //head
                node.setNext(head);
                head.setPrevious(node);
                head = node;
            }else if(comparator.compare(element, tail.getElement()) >= 0){ //tail
                node.setPrevious(tail);
                tail.setNext(node);
                tail = node;
            }else{
                
                DoublyListNode<E> current = head;
                while(current != null){
                    if(comparator.compare(element, current.getElement()) <= 0){

                        DoublyListNode<E> previous = current.getPrevious();
                        node.setNext(current);
                        node.setPrevious(previous);
                        previous.setNext(node);
                        current.setPrevious(node);
                        break;
                    }
                    current = current.getNext();
                }
            }
        }
        currentSize++;
    }

    /**
     * Removes and returns the first occurrence of the element equals to the given element in the list.
     * @return element removed from the list or null if !belongs(element)
     */
    public E remove(E element) {
        DoublyListNode<E> node = getNode(element);
        if(node == null) return null;
        E el = node.getElement(); //assumes uses of contains before

        if(currentSize == 1){ 
            node = head = tail = null;
        }else{
            if(node == head){ 
            head = node.getNext();
            head.setPrevious(null);
            }
            else if(node == tail){
                tail = node.getPrevious();
                tail.setNext(null);
            }
            else{
                node.getPrevious().setNext(node.getNext()); // previous -> next
                node.getNext().setPrevious(node.getPrevious()); //next -> previous
            }
        }

        currentSize--;
        return el;
    }

    //helpers
    
    /**
     * gets the first ocurrence of the element's node in the list or null if the element is not in the list
     * @param element to be found
     * @return node in the list or null
     */
    private DoublyListNode<E> getNode(E element) {
        DoublyListNode<E> node = head;

        while(node != null){
            if(comparator.compare(node.getElement(), element) == 0) return node;
            node = node.getNext();
        }
    return null;
    }

    /**
     * gets the first ocurrence of the element in the list or null if the element is not in the list
     * @param element to be found
     * @return element in the list or null
     */
    private E getEl(E element){
        DoublyListNode<E> node = head;

        while(node != null){
            if(comparator.compare(node.getElement(), element) == 0) return node.getElement();
            node = node.getNext();
        }
    return null;
    }

}
