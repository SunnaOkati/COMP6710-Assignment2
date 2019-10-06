package comp1110.ass2;

import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

import static comp1110.ass2.Colors.*;

public enum PieceType {
    a, b, c, d, e, f, g, h, i, j;


    public static Colors[][] pieceA = {
            {Colors.GREEN, Colors.WHITE, Colors.RED},
            {null, Colors.RED, null}
    };
    public static Colors[][] pieceB = {
            {null, Colors.BLUE, Colors.GREEN, Colors.GREEN},
            {Colors.WHITE, Colors.WHITE, null, null}
    };
    public static Colors[][] pieceC = {
            {null, null, Colors.GREEN, null},
            {Colors.RED, Colors.RED, Colors.WHITE, Colors.BLUE}
    };
    public static Colors[][] pieceD = {
            {Colors.RED, Colors.RED, Colors.RED},
            {null, null, Colors.BLUE}
    };
    public static Colors[][] pieceE = {
            {Colors.BLUE, Colors.BLUE, Colors.BLUE},
            {Colors.RED, Colors.RED,null }
    };
    public static Colors[][] pieceF = {
            {Colors.WHITE, Colors.WHITE, Colors.WHITE}
    };
    public static Colors[][] pieceG = {
            {Colors.WHITE, Colors.BLUE, null},
            {null, Colors.BLUE, Colors.WHITE}
    };
    public static Colors[][] pieceH = {
            {Colors.RED, Colors.GREEN, Colors.GREEN},
            {Colors.WHITE, null, null},
            {Colors.WHITE, null, null}
    };
    public static Colors[][] pieceI = {
            {Colors.BLUE, Colors.BLUE},
            {null, Colors.WHITE}
    };
    public static Colors[][] pieceJ = {
            {Colors.GREEN, Colors.GREEN, Colors.WHITE, Colors.RED},
            {Colors.GREEN, null, null, null}
    };



    //find the corresponding coordinate after rotation based on original x,y and the length, width of the piece
    //@author Rong Hu
    public static Location rotateXY(int x, int y, int length, int width, int orientation) {
        int xoff = 0;
        int yoff = 0;
        length--;
        width--;
        switch (orientation) {
            case 0:
                xoff=x;
                yoff=y;
                break;
            case 1:
                xoff=width-y;
                yoff=x;
                break;
            case 2:
                xoff=length-x;
                yoff=width-y;
                break;
            case 3:
                xoff=y;
                yoff=length-x;
                break;
        }
        Location location = new Location(xoff, yoff);
        return location;
    }

    public Colors[][] getPieceColors(char t){
        Colors[][] tempPiece = null;
        switch ((char)(t+97)){
            case 'a':
                tempPiece=PieceType.pieceA;
                break;
            case 'b':
                tempPiece=PieceType.pieceB;
                break;
            case 'c':
                tempPiece=PieceType.pieceC;
                break;
            case 'd':
                tempPiece=PieceType.pieceD;
                break;
            case 'e':
                tempPiece=PieceType.pieceE;
                break;
            case 'f':
                tempPiece=PieceType.pieceF;
                break;
            case 'g':
                tempPiece=PieceType.pieceG;
                break;
            case 'h':
                tempPiece=PieceType.pieceH;
                break;
            case 'i':
                tempPiece=PieceType.pieceI;
                break;
            case 'j':
                tempPiece=PieceType.pieceJ;
                break;
        }
        return tempPiece;
    }


}
