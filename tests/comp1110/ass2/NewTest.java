package comp1110.ass2;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class NewTest {
    FocusGame focusGame=new FocusGame();


    @Test
    public void test_deadCell(){
        Set<String> deadCellTestSet=new HashSet<>();
        deadCellTestSet.add("a001");
        deadCellTestSet.add("a703");
        deadCellTestSet.add("a020");
        deadCellTestSet.add("b430");
        deadCellTestSet.add("g621");
        Iterator<String> iter=deadCellTestSet.iterator();
        while (iter.hasNext()){
            String testSet=iter.next();
            boolean testDeadCell=FocusGame.isDeadCell(testSet);
            Assert.assertTrue(testSet+"can generate a deadcell! ",testDeadCell);
        }

    }

    @Test
    public void test_fillBoard(){
        Set<String> fillBoardTestSet=new HashSet<>();
        fillBoardTestSet.add("a000");               //top-left
        fillBoardTestSet.add("b512");               //top-right
        fillBoardTestSet.add("c711");               //bottom-right
        Iterator<String> iter=fillBoardTestSet.iterator();
        boolean sig=true;
        while (iter.hasNext()){
            Colors[][] boardState = new Colors[5][9];
            String testSet=iter.next();
            int x=(int)testSet.charAt(1)-48;
            int y=(int)testSet.charAt(2)-48;
            int orientation=testSet.charAt(3)-48;
            boardState=FocusGame.fillBoard(testSet,boardState);
            System.out.println(boardState[0][0]);
            Colors[][] testPiece=Piece.placementToPieceArray(testSet);
            int length=testPiece[0].length;
            int width=testPiece.length;
            for (int j=0;j<width;j++){
                for (int k=0;k<length;k++){
                    Location rotateLoc=PieceType.rotateXY(k,j,length,width,orientation);
                    if (boardState[y+rotateLoc.getY()][x+rotateLoc.getX()]!=testPiece[j][k]){
                        sig=false;
                    }
                }
            }
            Assert.assertTrue(testSet+" cannot be filled on the board ",sig);
        }
    }

    @Test
    public void test_rotateXY(){
        Set<String> rotateTestSet=new HashSet<>();
        rotateTestSet.add("a021");               //bottom-left
        rotateTestSet.add("b512");               //top-right
        rotateTestSet.add("c703");               //bottom-right
        Iterator<String> iter=rotateTestSet.iterator();
        while (iter.hasNext()){
            String testSet=iter.next();
            int x=(int)testSet.charAt(1)-48;
            int y=(int)testSet.charAt(2)-48;
            int orientation=testSet.charAt(3)-48;
            Colors[][] testPiece=Piece.placementToPieceArray(testSet);
            int length=testPiece[0].length;
            int width=testPiece.length;
            List<Location> rotateResult = new ArrayList<Location>();
            for (int j=0;j<width;j++){
                for (int k=0;k<length;k++){
                    Location rotateLoc=PieceType.rotateXY(k,j,length,width,orientation);
                    rotateResult.add(rotateLoc);
                }
            }
            switch (testSet){
                case "a021":
                    Assert.assertEquals(1,rotateResult.get(0).getX());
                    Assert.assertEquals(0,rotateResult.get(0).getY());
                    Assert.assertEquals(1,rotateResult.get(1).getX());
                    Assert.assertEquals(1,rotateResult.get(1).getY());
                    Assert.assertEquals(1,rotateResult.get(2).getX());
                    Assert.assertEquals(2,rotateResult.get(2).getY());
                    Assert.assertEquals(0,rotateResult.get(3).getX());
                    Assert.assertEquals(0,rotateResult.get(3).getY());
                    Assert.assertEquals(0,rotateResult.get(4).getX());
                    Assert.assertEquals(1,rotateResult.get(4).getY());
                    Assert.assertEquals(0,rotateResult.get(5).getX());
                    Assert.assertEquals(2,rotateResult.get(5).getY());
                    break;
                case "b512":
                    Assert.assertEquals(3,rotateResult.get(0).getX());
                    Assert.assertEquals(1,rotateResult.get(0).getY());
                    Assert.assertEquals(2,rotateResult.get(1).getX());
                    Assert.assertEquals(1,rotateResult.get(1).getY());
                    Assert.assertEquals(1,rotateResult.get(2).getX());
                    Assert.assertEquals(1,rotateResult.get(2).getY());
                    Assert.assertEquals(0,rotateResult.get(3).getX());
                    Assert.assertEquals(1,rotateResult.get(3).getY());
                    Assert.assertEquals(3,rotateResult.get(4).getX());
                    Assert.assertEquals(0,rotateResult.get(4).getY());
                    Assert.assertEquals(2,rotateResult.get(5).getX());
                    Assert.assertEquals(0,rotateResult.get(5).getY());
                    Assert.assertEquals(1,rotateResult.get(6).getX());
                    Assert.assertEquals(0,rotateResult.get(6).getY());
                    Assert.assertEquals(0,rotateResult.get(7).getX());
                    Assert.assertEquals(0,rotateResult.get(7).getY());
                    break;
                case "c703":
                    Assert.assertEquals(0,rotateResult.get(0).getX());
                    Assert.assertEquals(3,rotateResult.get(0).getY());
                    Assert.assertEquals(0,rotateResult.get(1).getX());
                    Assert.assertEquals(2,rotateResult.get(1).getY());
                    Assert.assertEquals(0,rotateResult.get(2).getX());
                    Assert.assertEquals(1,rotateResult.get(2).getY());
                    Assert.assertEquals(0,rotateResult.get(3).getX());
                    Assert.assertEquals(0,rotateResult.get(3).getY());
                    Assert.assertEquals(1,rotateResult.get(4).getX());
                    Assert.assertEquals(3,rotateResult.get(4).getY());
                    Assert.assertEquals(1,rotateResult.get(5).getX());
                    Assert.assertEquals(2,rotateResult.get(5).getY());
                    Assert.assertEquals(1,rotateResult.get(6).getX());
                    Assert.assertEquals(1,rotateResult.get(6).getY());
                    Assert.assertEquals(1,rotateResult.get(7).getX());
                    Assert.assertEquals(0,rotateResult.get(7).getY());
                    break;
            }
        }
    }

    @Test
    public void test_getPiece(){
        Set<String> rotateTestSet=new HashSet<>();
        rotateTestSet.add("a021");               //bottom-left
        rotateTestSet.add("b512");               //top-right
        rotateTestSet.add("c703");               //bottom-right
        Iterator<String> iter=rotateTestSet.iterator();
        String wrong="";
        boolean sig=true;
        while (iter.hasNext()){
            String testSet=iter.next();
            Colors[][] getPiece=Piece.placementToPieceArray(testSet);
            int length=getPiece[0].length;
            int width=getPiece.length;
            switch (testSet.charAt(0)){
                case 'a':
                    for (int j=0;j<width;j++){
                        for (int k=0;k<length;k++){
                            if (PieceType.pieceB[j][k]!=getPiece[j][k]){
                                sig=false;
                            }
                        }
                    }
                    if (sig==false){
                        wrong=wrong+"A, ";
                    }
                    break;
                case 'b':
                    for (int j=0;j<width;j++){
                        for (int k=0;k<length;k++){
                            if (PieceType.pieceB[j][k]!=getPiece[j][k]){
                                sig=false;
                            }
                        }
                    }
                    if (sig==false){
                        wrong=wrong+"B, ";
                    }
                    break;
                case 'c':
                    for (int j=0;j<width;j++){
                        for (int k=0;k<length;k++){
                            if (PieceType.pieceC[j][k]!=getPiece[j][k]){
                                sig=false;
                            }
                        }
                    }
                    if (sig==false){
                        wrong=wrong+"C, ";
                    }
                    break;
            }
        }
        Assert.assertTrue("get wrong piece: "+wrong+"! ",sig);
    }



}
