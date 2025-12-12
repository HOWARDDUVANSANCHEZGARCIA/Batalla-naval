package com.battleship.view.shipDrawers;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class DestructorDrawer implements ShipDrawer {

    @Override
    public void draw(Group parent, double width, double height, boolean isHorizontal) {
        if (isHorizontal) {
            drawHorizontal(parent, width, height);
        } else {
            drawVertical(parent, width, height);
        }
    }

    private void drawHorizontal(Group parent, double width, double height) {
        double svgWidth = 200.0;
        double svgHeight = 100.0;
        Polygon hull = new Polygon();
        hull.getPoints().addAll(0.123/svgWidth*width,50.616/svgHeight*height,200.246/svgWidth*width,51.232/svgHeight*height,178.695/svgWidth*width,99.877/svgHeight*height,16.749/svgWidth*width,99.261/svgHeight*height,1.355/svgWidth*width,51.847/svgHeight*height);
        hull.setFill(Color.web("#C2C1C1"));
        hull.setStroke(Color.BLACK);
        hull.setStrokeWidth(1.5);
        parent.getChildren().add(hull);
        Polygon tower = new Polygon();
        tower.getPoints().addAll(17.365/svgWidth*width,50.0/svgHeight*height,26.601/svgWidth*width,29.68/svgHeight*height,100.493/svgWidth*width,30.614/svgHeight*height,107.86/svgWidth*width,50.572/svgHeight*height);
        tower.setFill(Color.web("#979797"));
        tower.setStroke(Color.BLACK);
        tower.setStrokeWidth(1.5);
        parent.getChildren().add(tower);
        Polygon chimney = new Polygon();
        chimney.getPoints().addAll(157.627/svgWidth*width,50.969/svgHeight*height,163.567/svgWidth*width,44.139/svgHeight*height,191.183/svgWidth*width,44.139/svgHeight*height,196.528/svgWidth*width,50.672/svgHeight*height);
        chimney.setFill(Color.web("#4A4A4A"));
        chimney.setStroke(Color.BLACK);
        chimney.setStrokeWidth(1.5);
        parent.getChildren().add(chimney);
        Line line1 = new Line(177.226/svgWidth*width,44.139/svgHeight*height,187.917/svgWidth*width,34.043/svgHeight*height);
        line1.setStroke(Color.BLACK);
        line1.setStrokeWidth(1);
        parent.getChildren().add(line1);
        Polygon mast = new Polygon();
        mast.getPoints().addAll(50.427/svgWidth*width,29.886/svgHeight*height,56.663/svgWidth*width,0.19/svgHeight*height,66.463/svgWidth*width,0.19/svgHeight*height,69.135/svgWidth*width,29.886/svgHeight*height);
        mast.setFill(Color.web("#979797"));
        mast.setStroke(Color.BLACK);
        mast.setStrokeWidth(1.5);
        parent.getChildren().add(mast);
        Polygon redBase = new Polygon();
        redBase.getPoints().addAll(11.823/svgWidth*width,83.337/svgHeight*height,185.541/svgWidth*width,84.228/svgHeight*height,178.711/svgWidth*width,99.67/svgHeight*height,16.574/svgWidth*width,99.373/svgHeight*height,11.526/svgWidth*width,83.634/svgHeight*height);
        redBase.setFill(Color.web("#E21A1A"));
        redBase.setStroke(Color.BLACK);
        redBase.setStrokeWidth(1.5);
        parent.getChildren().add(redBase);
        Rectangle window1 = new Rectangle(103.879/svgWidth*width,65.817/svgHeight*height,75.723/svgWidth*width,10.393/svgHeight*height);
        window1.setFill(Color.web("#92A2B4"));
        window1.setStroke(Color.BLACK);
        window1.setStrokeWidth(1);
        parent.getChildren().add(window1);
        Rectangle window2 = new Rectangle(17.169/svgWidth*width,65.52/svgHeight*height,80.475/svgWidth*width,10.69/svgHeight*height);
        window2.setFill(Color.web("#92A2B4"));
        window2.setStroke(Color.BLACK);
        window2.setStrokeWidth(1);
        parent.getChildren().add(window2);
    }

    private void drawVertical(Group parent, double width, double height) {
        double svgWidth = 100.0;
        double svgHeight = 200.0;
        Polygon hull = new Polygon();
        hull.getPoints().addAll(50.616/svgWidth*width,0.123/svgHeight*height,51.232/svgWidth*width,200.246/svgHeight*height,99.877/svgWidth*width,178.695/svgHeight*height,99.261/svgWidth*width,16.749/svgHeight*height,51.847/svgWidth*width,1.355/svgHeight*height);
        hull.setFill(Color.web("#C2C1C1"));
        hull.setStroke(Color.BLACK);
        hull.setStrokeWidth(1.5);
        parent.getChildren().add(hull);
        Polygon tower = new Polygon();
        tower.getPoints().addAll(50.0/svgWidth*width,17.365/svgHeight*height,29.68/svgWidth*width,26.601/svgHeight*height,30.614/svgWidth*width,100.493/svgHeight*height,50.572/svgWidth*width,107.86/svgHeight*height);
        tower.setFill(Color.web("#979797"));
        tower.setStroke(Color.BLACK);
        tower.setStrokeWidth(1.5);
        parent.getChildren().add(tower);
        Polygon chimney = new Polygon();
        chimney.getPoints().addAll(50.969/svgWidth*width,157.627/svgHeight*height,44.139/svgWidth*width,163.567/svgHeight*height,44.139/svgWidth*width,191.183/svgHeight*height,50.672/svgWidth*width,196.528/svgHeight*height);
        chimney.setFill(Color.web("#4A4A4A"));
        chimney.setStroke(Color.BLACK);
        chimney.setStrokeWidth(1.5);
        parent.getChildren().add(chimney);
        Line line1 = new Line(44.139/svgWidth*width,177.226/svgHeight*height,34.043/svgWidth*width,187.917/svgHeight*height);
        line1.setStroke(Color.BLACK);
        line1.setStrokeWidth(1);
        parent.getChildren().add(line1);
        Polygon mast = new Polygon();
        mast.getPoints().addAll(29.886/svgWidth*width,50.427/svgHeight*height,0.19/svgWidth*width,56.663/svgHeight*height,0.19/svgWidth*width,66.463/svgHeight*height,29.886/svgWidth*width,69.135/svgHeight*height);
        mast.setFill(Color.web("#979797"));
        mast.setStroke(Color.BLACK);
        mast.setStrokeWidth(1.5);
        parent.getChildren().add(mast);
        Polygon redBase = new Polygon();
        redBase.getPoints().addAll(83.337/svgWidth*width,11.823/svgHeight*height,84.228/svgWidth*width,185.541/svgHeight*height,99.67/svgWidth*width,178.711/svgHeight*height,99.373/svgWidth*width,16.574/svgHeight*height,83.634/svgWidth*width,11.526/svgHeight*height);
        redBase.setFill(Color.web("#E21A1A"));
        redBase.setStroke(Color.BLACK);
        redBase.setStrokeWidth(1.5);
        parent.getChildren().add(redBase);
        Rectangle window1 = new Rectangle(65.817/svgWidth*width,103.879/svgHeight*height,10.393/svgWidth*width,75.723/svgHeight*height);
        window1.setFill(Color.web("#92A2B4"));
        window1.setStroke(Color.BLACK);
        window1.setStrokeWidth(1);
        parent.getChildren().add(window1);
        Rectangle window2 = new Rectangle(65.52/svgWidth*width,17.169/svgHeight*height,10.69/svgWidth*width,80.475/svgHeight*height);
        window2.setFill(Color.web("#92A2B4"));
        window2.setStroke(Color.BLACK);
        window2.setStrokeWidth(1);
        parent.getChildren().add(window2);
    }
}
