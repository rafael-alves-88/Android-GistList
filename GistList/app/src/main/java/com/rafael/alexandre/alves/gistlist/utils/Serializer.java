package com.rafael.alexandre.alves.gistlist.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

public class Serializer {

    private Context ctx;

    public Serializer(Context ctx){
        this.ctx = ctx;
    }

    public void Write(String filename, Object c) throws IOException {

        FileOutputStream fos = this.ctx.openFileOutput(filename, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(c);
        os.close();
        fos.close();
    }

    public Object Read(String filename) throws IOException, ClassNotFoundException {

        FileInputStream fis = this.ctx.openFileInput(filename);
        ObjectInputStream is = new ObjectInputStream(fis);
        Object simpleClass = is.readObject();

        is.close();
        fis.close();

        return simpleClass;
    }

    public void writeString(String filename, String content) throws IOException{

        OutputStreamWriter osWriter = new OutputStreamWriter(this.ctx.openFileOutput(filename, Context.MODE_PRIVATE));
        osWriter.write(content);
        osWriter.close();
    }

    public String readString(String filename) throws IOException{

        InputStream inputStream = this.ctx.openFileInput(filename);
        String ret = "";

        if ( inputStream != null ) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString;
            StringBuilder stringBuilder = new StringBuilder();

            while ( (receiveString = bufferedReader.readLine()) != null ) {
                stringBuilder.append(receiveString);
            }

            inputStream.close();
            ret = stringBuilder.toString();
        }

        return ret;
    }

    public void deleteFile(String filename) throws IOException{
        this.ctx.deleteFile(filename);
    }
}