package com.battleship.view.shipDrawers;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class PortaavionesDrawer implements ShipDrawer {

    @Override
    public void draw(Group parent, double width, double height, boolean isHorizontal) {
        if (isHorizontal) {
            drawHorizontal(parent, width, height);
        } else {
            // Dibujar horizontal en un grupo interno usando el LADO LARGO como ancho
            double longSide = height;   // en vertical el lado largo es la altura (4 celdas)
            double shortSide = width;   // lado corto es el ancho (1 celda)

            Group temp = new Group();
            // Dibuja como horizontal: largo x corto
            drawHorizontal(temp, longSide, shortSide);

            // Pivot en el centro del área largo x corto
            double pivotX = longSide / 2.0;
            double pivotY = shortSide / 2.0;

            // Mover el contenido para que el pivot quede en (0,0)
            temp.setTranslateX(-pivotX);
            temp.setTranslateY(-pivotY);

            // Rotar 90° alrededor de (0,0)
            temp.setRotate(90);

            // Ahora colocamos el grupo rotado de forma que ocupe width x height:
            // después de rotar, el tamaño visible queda shortSide x longSide,
            // queremos que quede centrado en width x height (shortSide x longSide).
            temp.setTranslateX(temp.getTranslateX() + width / 2.0);
            temp.setTranslateY(temp.getTranslateY() + height / 2.0);

            parent.getChildren().add(temp);
        }
    }

    private void drawHorizontal(Group parent, double width, double height) {
        double svgWidth = 500.0;
        double minY = 200.039;
        double maxY = 299.347;
        double svgHeight = maxY - minY;

        Polygon hull = new Polygon();
        hull.getPoints().addAll(
                48.094 / svgWidth * width, (299.347 - minY) / svgHeight * height,
                457.576 / svgWidth * width, (298.584 - minY) / svgHeight * height,
                502.497 / svgWidth * width, (265.052 - minY) / svgHeight * height,
                303.605 / svgWidth * width, (264.289 - minY) / svgHeight * height,
                281.414 / svgWidth * width, (237.616 - minY) / svgHeight * height,
                1.242 / svgWidth * width, (237.235 - minY) / svgHeight * height
        );
        hull.setFill(Color.web("#A2A2A2"));
        hull.setStroke(Color.BLACK);
        hull.setStrokeWidth(1.5);
        parent.getChildren().add(hull);

        Polygon tower1 = new Polygon();
        tower1.getPoints().addAll(
                364.565 / svgWidth * width, (264.289 - minY) / svgHeight * height,
                385.478 / svgWidth * width, (237.235 - minY) / svgHeight * height,
                441.512 / svgWidth * width, (237.235 - minY) / svgHeight * height,
                440.92 / svgWidth * width, (264.289 - minY) / svgHeight * height
        );
        tower1.setFill(Color.web("#777777"));
        tower1.setStroke(Color.web("#2E2D2D"));
        tower1.setStrokeWidth(1.5);
        parent.getChildren().add(tower1);

        Polygon frontStructure = new Polygon();
        frontStructure.getPoints().addAll(
                68.222 / svgWidth * width, (237.097 - minY) / svgHeight * height,
                102.347 / svgWidth * width, (208.767 - minY) / svgHeight * height,
                201.826 / svgWidth * width, (208.92 - minY) / svgHeight * height,
                236.248 / svgWidth * width, (237.097 - minY) / svgHeight * height
        );
        frontStructure.setFill(Color.web("#777777"));
        frontStructure.setStroke(Color.BLACK);
        frontStructure.setStrokeWidth(1.5);
        parent.getChildren().add(frontStructure);

        Rectangle window1 = new Rectangle(
                68.983 / svgWidth * width, (245.863 - minY) / svgHeight * height,
                170.006 / svgWidth * width, 6.738 / svgHeight * height
        );
        window1.setFill(Color.web("#91B6BE"));
        window1.setStroke(Color.BLACK);
        window1.setStrokeWidth(1);
        window1.setArcWidth(5.444 / svgWidth * width);
        window1.setArcHeight(5.196 / svgHeight * height);
        parent.getChildren().add(window1);

        Rectangle window2 = new Rectangle(
                68.123 / svgWidth * width, (257.426 - minY) / svgHeight * height,
                170.748 / svgWidth * width, 6.432 / svgHeight * height
        );
        window2.setFill(Color.web("#91B6BE"));
        window2.setStroke(Color.BLACK);
        window2.setStrokeWidth(1);
        window2.setArcWidth(5.196 / svgWidth * width);
        window2.setArcHeight(5.196 / svgHeight * height);
        parent.getChildren().add(window2);

        Rectangle window3 = new Rectangle(
                68.44 / svgWidth * width, (268.795 - minY) / svgHeight * height,
                170.254 / svgWidth * width, 6.432 / svgHeight * height
        );
        window3.setFill(Color.web("#91B6BE"));
        window3.setStroke(Color.BLACK);
        window3.setStrokeWidth(1);
        window3.setArcWidth(5.196 / svgWidth * width);
        window3.setArcHeight(5.196 / svgHeight * height);
        parent.getChildren().add(window3);

        Line waterline = new Line(
                37.886 / svgWidth * width, (285.258 - minY) / svgHeight * height,
                475.275 / svgWidth * width, (285.258 - minY) / svgHeight * height
        );
        waterline.setStroke(Color.web("#DD2B2B"));
        waterline.setStrokeWidth(3);
        parent.getChildren().add(waterline);

        Rectangle container1 = new Rectangle(
                307.922 / svgWidth * width, (272.432 - minY) / svgHeight * height,
                44.234 / svgWidth * width, 7.657 / svgHeight * height
        );
        container1.setFill(Color.web("#D8D8D8"));
        container1.setStroke(Color.BLACK);
        container1.setStrokeWidth(1);
        parent.getChildren().add(container1);

        Rectangle container2 = new Rectangle(
                359.425 / svgWidth * width, (272.432 - minY) / svgHeight * height,
                44.234 / svgWidth * width, 7.657 / svgHeight * height
        );
        container2.setFill(Color.web("#D8D8D8"));
        container2.setStroke(Color.BLACK);
        container2.setStrokeWidth(1);
        parent.getChildren().add(container2);

        Rectangle container3 = new Rectangle(
                411.082 / svgWidth * width, (272.432 - minY) / svgHeight * height,
                44.234 / svgWidth * width, 7.657 / svgHeight * height
        );
        container3.setFill(Color.web("#D8D8D8"));
        container3.setStroke(Color.BLACK);
        container3.setStrokeWidth(1);
        parent.getChildren().add(container3);

        Polygon redBase = new Polygon();
        redBase.getPoints().addAll(
                37.579 / svgWidth * width, (285.258 - minY) / svgHeight * height,
                48.109 / svgWidth * width, (299.231 - minY) / svgHeight * height,
                457.656 / svgWidth * width, (298.466 - minY) / svgHeight * height,
                474.968 / svgWidth * width, (285.45 - minY) / svgHeight * height
        );
        redBase.setFill(Color.web("#DD2B2B"));
        redBase.setStroke(Color.BLACK);
        redBase.setStrokeWidth(1.5);
        parent.getChildren().add(redBase);
    }
}
