package dataStructures;

import dataStructures.exceptions.*;

/**
 * Stack in Array
 *
 * @author AED team
 * @version 1.0
 *
 * @param <E> Generic Element
 */
public class StackWithListInArray<E> implements Stack<E> {

    // Default capacity of the stack.
    static final int DEFAULT_CAPACITY = 1000;
    // Top of the empty stack.
    static final int EMPTY = -1;

    // Memory of the stack: a list in array.
    private List<E> array;
    // capacity
    private int capacity;

    //last elemet position
    private int top;

    public StackWithListInArray( int capacity ) {
        array =  new ListInArray(capacity);
	    this.capacity=capacity;
        this.top = EMPTY;
        
    }

    public StackWithListInArray( ) {
        this(DEFAULT_CAPACITY);
    }
    /**
     * Returns true iff the stack contains no
     * elements.
     *
     * @return true iff the stack contains no
     * elements, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return top == EMPTY;
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return number of elements in the stack
     */
    @Override
    public int size() {
        return top + 1;
    }

    /**
     * Returns the element at the top of the stack.
     * Requires
     *
     * @return element at top of stack
     * @throws EmptyStackException when size = 0
     */
    @Override
    public E top() {
       if(this.isEmpty()) throw new EmptyStackException();
       return array.get(top);
    }

    /**
     * Inserts the specified <code>element</code> onto
     * the top of the stack.
     *
     * @param element element to be inserted onto the stack
     * @throws FullStackException when size = capacity
     */
    @Override
    public void push(E element) {
        if(this.isFull()) throw new FullStackException();
        
        top++;
        array.add(top, element);
    }

    
    /**
     * Removes and returns the element at the top of the
     * stack.
     *
     * @return element removed from top of stack
     * @throws EmptyStackException when size = 0
     */
    @Override
    public E pop() {
       if(this.isEmpty()) throw new EmptyStackException();

       E element = array.get(top);
       array.remove(top);
       top--;
       return element;
    }

    // ============== helpers ==============

    /**
     * 
     * @return true if the size = capacity
     */
    private boolean isFull() {
        return this.size() == capacity;
    }
}
