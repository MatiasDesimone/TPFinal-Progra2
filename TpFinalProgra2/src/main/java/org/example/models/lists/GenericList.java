package org.example.models.lists;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericList <T>{
    private List<T> list;

    public GenericList(){
        this.list = new ArrayList<>();
    }

    public GenericList(int size){
        this.list = new ArrayList<>(size);
    }

    public void add(T item){
        this.list.add(item);
    }

    public void remove(T item){
        this.list.remove(item);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
