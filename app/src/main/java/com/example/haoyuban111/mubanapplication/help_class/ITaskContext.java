package com.example.haoyuban111.mubanapplication.help_class;

public interface ITaskContext<T> {
    void onCompleted(T result);
}