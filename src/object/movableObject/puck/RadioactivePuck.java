package object.movableObject.puck;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;

public class RadioactivePuck extends Puck{

    public RadioactivePuck(double radius, double height)
    {
        super(radius, height);
        double angle = Math.toRadians(30);
        double cylinderR = 0.2 * radius;
        //double triangleR = 0.8 * radius;

        float[] points = new float[6 * 3];

        float[] texCoords = new float[]{
          0.5f, 0,
          0, 1,
          1, 1
        };

        int[] faces = new int[(2 + 3 * 2) * 2 * 6];

        points[0] = 0;
        points[1] = -(float) height / 2;
        points[2] = 0;

        points[3] = (float)radius;
        points[4] = -(float) height / 2;
        points[5] = 0;

        points[6] = (float)(radius * Math.cos(angle));
        points[7] = - (float) height / 2;
        points[8] = (float)(radius * Math.sin(angle));

        points[9] = 0;
        points[10] = (float) height / 2;
        points[11] = 0;

        points[12] = (float)radius;
        points[13] = (float) height / 2;
        points[14] = 0;

        points[15] = (float)(radius * Math.cos(angle));
        points[16] = (float) height / 2;
        points[17] = (float)(radius * Math.sin(angle));

        for(int i = 0; i < 2; i++)
        {
            faces[i * 12 + 0] = i * 3 + 0;
            faces[i * 12 + 1] = 0;

            faces[i * 12 + 2] = i * 3 + 1;
            faces[i * 12 + 3] = 1;

            faces[i * 12 + 4] = i * 3 + 2;
            faces[i * 12 + 5] = 2;

            faces[i * 12 + 6] = i * 3 + 0;
            faces[i * 12 + 7] = 0;

            faces[i * 12 + 8] = i * 3 + 2;
            faces[i * 12 + 9] = 2;

            faces[i * 12 + 10] = i * 3 + 1;
            faces[i * 12 + 11] = 1;
        }

        for(int i = 0; i < 3; i++)
        {
            faces[2 * 12 + i * 12 + 0] = i;
            faces[2 * 12 + i * 12 + 1] = 0;

            faces[2 * 12 + i * 12 + 2] = 3 + i;
            faces[2 * 12 + i * 12 + 3] = 1;

            faces[2 * 12 + i * 12 + 4] = 3 + (i + 1) % 3;
            faces[2 * 12 + i * 12 + 5] = 2;

            faces[2 * 12 + i * 12 + 6] = i;
            faces[2 * 12 + i * 12 + 7] = 0;

            faces[2 * 12 + i * 12 + 8] = 3 + (i + 1) % 3;
            faces[2 * 12 + i * 12 + 9] = 2;

            faces[2 * 12 + i * 12 + 10] = 3 + i;
            faces[2 * 12 + i * 12 + 11] = 1;
        }

        for(int i = 0; i < 3; i++)
        {
            faces[5 * 12 + i * 12 + 0] = 3 + i;
            faces[5 * 12 + i * 12 + 1] = 0;

            faces[5 * 12 + i * 12 + 2] = i;
            faces[5 * 12 + i * 12 + 3] = 1;

            faces[5 * 12 + i * 12 + 4] = (i - 1) < 0 ? 2 : i - 1;
            faces[5 * 12 + i * 12 + 5] = 2;

            faces[5 * 12 + i * 12 + 6] = 3 + i;
            faces[5 * 12 + i * 12 + 7] = 0;

            faces[5 * 12 + i * 12 + 8] = (i - 1) < 0 ? 2 : i - 1;
            faces[5 * 12 + i * 12 + 9] = 2;

            faces[5 * 12 + i * 12 + 10] = i;
            faces[5 * 12 + i * 12 + 11] = 1;
        }

        TriangleMesh triangleMesh = new TriangleMesh();
        triangleMesh.getPoints().addAll(points);
        triangleMesh.getTexCoords().addAll(texCoords);
        triangleMesh.getFaces().addAll(faces);

        MeshView[] triangle = new MeshView[3];
        PhongMaterial triangleMaterial = new PhongMaterial(Color.YELLOW);
        double dAngle = 120.0;

        for(int i = 0; i < triangle.length; i++)
        {
            triangle[i] = new MeshView();
            triangle[i].setMesh(triangleMesh);
            triangle[i].setMaterial(triangleMaterial);
            triangle[i].getTransforms().add(new Rotate(dAngle * i, Rotate.Y_AXIS));

            super.getChildren().add(triangle[i]);

            //triangle[i].setDrawMode(DrawMode.LINE);
        }

        Cylinder centerCylinder = new Cylinder(cylinderR, height);
        PhongMaterial cylinderMaterial = new PhongMaterial(Color.YELLOW);
        centerCylinder.setMaterial(cylinderMaterial);

        super.getChildren().add(centerCylinder);

    }

}
