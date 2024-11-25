package org.example;

import org.example.models.Address;

import java.util.*;

import static org.example.utils.Mocks.generateRandomAddress;

public class Comparacion {
    public static void main(String[] args) {
        int dataSize = 1000000; // Número de elementos a cargar

        // Listas
        compareList(new ArrayList<>(), dataSize, "ArrayList");
        compareList(new Vector<>(), dataSize, "Vector");
        // compareList(new ArrayDeque<>(), dataSize, "Deque");

        // Sets
        compareSet(new HashSet<>(), dataSize, "HashSet");
        compareSet(new LinkedHashSet<>(), dataSize, "LinkedHashSet");
        compareSet(new TreeSet<>(), dataSize, "TreeSet");

        // Maps
        compareMap(new HashMap<>(), dataSize, "HashMap");
        compareMap(new LinkedHashMap<>(), dataSize, "LinkedHashMap");
        compareMap(new TreeMap<>(), dataSize, "TreeMap");
    }

    private static void compareList(List<Address> list, int dataSize, String type) {
        long startTime = System.nanoTime();
        for (int i = 0; i < dataSize; i++) {
            list.add(generateRandomAddress());
        }
        long endTime = System.nanoTime();
        System.out.println(type + " - Tiempo de carga: " + (endTime - startTime) / 1e6 + " ms");

        Address lastAddress = list.get(dataSize - 1);
        startTime = System.nanoTime();
        list.contains(lastAddress);
        endTime = System.nanoTime();
        System.out.println(type + " - Tiempo de búsqueda del último elemento: " + (endTime - startTime) + " ns");
    }

    private static void compareSet(Set<Address> set, int dataSize, String type) {
        long startTime = System.nanoTime();
        List<Address> addresses = new ArrayList<>();
        for (int i = 0; i < dataSize; i++) {
            Address address = generateRandomAddress();
            set.add(address);
            addresses.add(address);
        }
        long endTime = System.nanoTime();
        System.out.println(type + " - Tiempo de carga: " + (endTime - startTime) / 1e6 + " ms");

        Address lastAddress = addresses.get(dataSize - 1);
        startTime = System.nanoTime();
        set.contains(lastAddress);
        endTime = System.nanoTime();
        System.out.println(type + " - Tiempo de búsqueda del último elemento: " + (endTime - startTime) + " ns");
    }

    private static void compareMap(Map<Integer, Address> map, int dataSize, String type) {
        long startTime = System.nanoTime();
        for (int i = 0; i < dataSize; i++) {
            map.put(i, generateRandomAddress());
        }
        long endTime = System.nanoTime();
        System.out.println(type + " - Tiempo de carga: " + (endTime - startTime) / 1e6 + " ms");

        startTime = System.nanoTime();
        map.containsKey(dataSize - 1);
        endTime = System.nanoTime();
        System.out.println(type + " - Tiempo de búsqueda del último elemento: " + (endTime - startTime) + " ns");
    }
}
