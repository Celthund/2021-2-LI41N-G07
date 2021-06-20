package pt.isel.ls.views.builders.html.parts;


import pt.isel.ls.views.builders.html.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Style extends Element {

    public Style(Element... elements) {
        super(elements);
    }

    public Style(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        StringBuilder css = new StringBuilder();
        try {
            File cssFile = new File("style.css");
            Scanner fileReader = new Scanner(cssFile);
            while (fileReader.hasNextLine()){
                css.append(fileReader.nextLine());
            }
        } catch (FileNotFoundException e){
            css.append("File Not Found");
        }

        return "<style>" + css;
    }

    @Override
    protected String endTag() {
        return "</style>";
    }
}
