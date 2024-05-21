/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructuresfinalproject;

/**
 *
 * @author lawrence
 */
class KVpair<Key, E> {

    private Key k;
    private E e;

    /**
     * Constructors
     */
    KVpair() {
        k = null;
        e = null;
    }

    KVpair(Key kval, E eval) {
        k = kval;
        e = eval;
    }

    /**
     * Data member access functions
     */
    public Key key() {
        return k;
    }

    public E value() {
        return e;
    }
    
    public String toString() {
        return "[" + k + ", " + e + "]";
    }
}

