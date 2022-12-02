/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comparisons;

/**
 *
 * @author dangh
 */
public class Widget implements Comparable {

    private int id;
    private String name;
    private int size;

    public Widget() {
    }

    public Widget(int id, String name, int size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        Widget w = (Widget) o;
        if (id == w.getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Object o) {
        Widget w = (Widget) o;

        if (size < w.getSize()) {
            return -1;
        } else if (size > w.getSize()) {
            return 1;
        } else {
            return 0;
        }
    }
}
