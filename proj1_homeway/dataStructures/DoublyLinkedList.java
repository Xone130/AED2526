package dataStructures;

import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;

/**
 * Doubly Linked List
 *
 * @author AED team
 * @version 1.0
 *
 * @param <E> Generic Element
 */
public class DoublyLinkedList<E> implements TwoWayList<E> {
    /**
     * Node at the head of the list.
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
     * Constructor of an empty double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.currentSize = 0;

    }

    /**
     * Returns true iff the list contains no elements.
     * 
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Returns the number of elements in the list.
     * 
     * @return number of elements in the list
     */

    public int size() {
        return currentSize;
    }

    /**
     * Returns a two-way iterator of the elements in the list.
     *
     * @return Two-Way Iterator of the elements in the list
     */

    public TwoWayIterator<E> twoWayiterator() {
        return new TwoWayDoublyIterator<>(head, tail);
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * 
     * @return Iterator of the elements in the li
     *         st
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Inserts the element at the first position in the list.
     * 
     * @param element - Element to be inserted
     */
    public void addFirst(E element) {
        DoublyListNode<E> newNode = new DoublyListNode<>(element, null, head);

        if (!this.isEmpty()) head.setPrevious(newNode);
        else tail = newNode;

        head = newNode;
        currentSize++;
    }

    /**
     * Inserts the element at the last position in the list.
     * 
     * @param element - Element to be inserted
     */
    public void addLast(E element) {
        if (this.isEmpty()){
            addFirst(element); 
            return;
        } 

        DoublyListNode<E> newNode = new DoublyListNode<>(element, tail, null);
        tail.setNext(newNode);

        tail = newNode;
        currentSize++;
    }

    /**
     * Returns the first element of the list.
     * 
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getFirst() {
        if (this.isEmpty()) throw new NoSuchElementException();
        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     * 
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getLast() {
        if (this.isEmpty()) throw new NoSuchElementException();
        return tail.getElement();
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
    public E get(int position) {
        if(position < 0 || position >= currentSize) throw new InvalidPositionException();
        if(position == 0) return getFirst();
        if(position == currentSize - 1) return getLast();

        return getNode(position).getElement();
    }

    /**
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     * 
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     */
    public int indexOf(E element) {
        
        DoublyListNode<E> node = head;
        int position = 0;

        while(node!= null && !node.getElement().equals(element)){
            node = node.getNext();
            position++;
        }
        if (node == null) return NOT_FOUND;
        return position;
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
    public void add(int position, E element) {
        if (position < 0 || position > currentSize) throw new InvalidPositionException();
        else if (position == 0) this.addFirst(element);
        else if (position == currentSize) this.addLast(element);
        else this.addMiddle(position, element);

    }

    /**
     * Removes and returns the element at the first position in the list.
     * 
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeFirst() {
        if(this.isEmpty()) throw new NoSuchElementException();

        E element = head.getElement();
        head = head.getNext();

        if(head == null) tail = null; // In case currentSize == 1 here
        else head.setPrevious(null);

        currentSize--;
        return element;
    }

    /**
     * Removes and returns the element at the last position in the list.
     * 
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeLast() {
        if(this.isEmpty()) throw new NoSuchElementException();
        if(size() == 1) return removeFirst();

        E element = tail.getElement();
        tail = tail.getPrevious();
        tail.setNext(null);

        currentSize--;
        return element;
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
    
    public E remove(int position) {
        if(position < 0 || position > currentSize - 1) throw new InvalidPositionException();
        if(position == 0) return removeFirst();
        if(position == currentSize - 1) return removeLast();
        else return this.removeMiddle(position);
    }

    private DoublyListNode<E> getNode(int position){
        DoublyListNode<E> node = head;

        for(int i = 0; i < position; i++){
            node = node.getNext();
        }
        
        return node;
    }

    private void addMiddle(int position, E element) {
        DoublyListNode<E> prevNode = this.getNode(position - 1);
        DoublyListNode<E> nextNode = prevNode.getNext();
        DoublyListNode<E> newNode = new DoublyListNode<>(element, prevNode, nextNode);

        prevNode.setNext(newNode);
        nextNode.setPrevious(newNode);
        currentSize++;
    }

    private E removeMiddle(int position){
        DoublyListNode<E> prevNode = this.getNode(position - 1);
        DoublyListNode<E> node = prevNode.getNext();
        DoublyListNode<E> nextNode = node.getNext();

        prevNode.setNext(nextNode);
        nextNode.setPrevious(prevNode);

        currentSize--;
        return node.getElement();
    }

}
