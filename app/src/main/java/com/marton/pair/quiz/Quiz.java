package com.marton.pair.quiz;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marton on 12/13/17.
 */

public class Quiz implements Serializable{
    private String name;
    private List<Pair> pairs;
    public static Context context;

    public Quiz(String name) {
        this.name = name;

        this.pairs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public static void save(Quiz q) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "quiz/"+q.getName()+".qz"));
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(q);
        os.close();
        fos.close();
    }

    public static Quiz load(String name) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "quiz/"+name+".qz"));
        ObjectInputStream is = new ObjectInputStream(fis);
        Quiz q = (Quiz) is.readObject();
        is.close();
        fis.close();
        return q;
    }
}
