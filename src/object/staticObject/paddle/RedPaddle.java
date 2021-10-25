package object.staticObject.paddle;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Translate;

public class RedPaddle extends Paddle{

    private static final int DIV = 10;
    private static final double dTHETA = 2 * Math.PI / DIV;
    private static final double dPHI = Math.PI / 2 / DIV;

    public RedPaddle(double width, double height, double depth )
    {
        super(width, height, depth);

        double semiSphereR = 0.2 * width;

//        float[] points = new float[(DIV + 1) * DIV * 3];
//        float[] textCoords = new float[]{
//                0.5f, 0,
//                0, 1,
//                1, 1
//        };
//        int[] faces = new int[DIV * DIV * 2 * 6];
//
//        for(int i = 0; i < DIV + 1; i++)
//        {
//            double phi = dPHI * i;
//            for(int j = 0; j < DIV; j++)
//            {
//                double theta = dTHETA * j;
//                points[i * DIV * 3 + j * 3 + 0] = (float)(semiSphereR * Math.sin(phi) * Math.sin(theta));
//                points[i * DIV * 3 + j * 3 + 1] = (float)( - semiSphereR * Math.cos(theta));
//                points[i * DIV * 3 + j * 3 + 2] = (float)( - semiSphereR * Math.cos(phi) * Math.sin(theta));
//
//            }
//        }
//
//        for(int i = 0; i < DIV - 1; i++)
//            for(int j = 0; j < DIV; j++)
//            {
//                faces[i * DIV * 12 + j * 12 + 0] = i * DIV + j;
//                faces[i * DIV * 12 + j * 12 + 1] = 0;
//
//                faces[i * DIV * 12 + j * 12 + 2] = (i + 1) * DIV + j;
//                faces[i * DIV * 12 + j * 12 + 3] = 1;
//
//                faces[i * DIV * 12 + j * 12 + 4] = (i + 1) * DIV + (j + 1) % DIV;
//                faces[i * DIV * 12 + j * 12 + 5] = 2;
//
//                faces[i * DIV * 12 + j * 12 + 6] = i * DIV + j;
//                faces[i * DIV * 12 + j * 12 + 7] = 0;
//
//                faces[i * DIV * 12 + j * 12 + 8] = (i + 1) * DIV + (j + 1) % DIV;
//                faces[i * DIV * 12 + j * 12 + 9] = 2;
//
//                faces[i * DIV * 12 + j * 12 + 10] = (i + 1) * DIV + j;
//                faces[i * DIV * 12 + j * 12 + 11] = 1;
//            }
//
//        TriangleMesh triangleMesh = new TriangleMesh();
//        triangleMesh.getPoints().addAll(points);
//        triangleMesh.getTexCoords().addAll(textCoords);
//        triangleMesh.getFaces().addAll(faces);
//
//        MeshView semiSphere = new MeshView();
//        semiSphere.setMesh(triangleMesh);
//        semiSphere.setMaterial(new PhongMaterial(Color.RED));
//        semiSphere.setDrawMode(DrawMode.LINE);
//        //semiSphere.getTransforms().add(new Translate(0, -300, 400));
//        super.getChildren().add(semiSphere);

        Sphere top = new Sphere(semiSphereR);
        top.setMaterial(new PhongMaterial(Color.RED));
        top.getTransforms().add(new Translate(0, -0.7 * height, 0));
        super.getChildren().add(top);

        Cylinder downCylinder = new Cylinder(0.4 * width, 0.2 * height);
        downCylinder.setMaterial(new PhongMaterial(Color.RED));
        super.getChildren().add(downCylinder);

        double RCylinder = semiSphereR;
        Cylinder handle = new Cylinder(RCylinder, 0.6 * height);
        handle.setMaterial(new PhongMaterial(Color.RED));
        super.getChildren().addAll(handle);
        handle.getTransforms().add(new Translate(0,  - 0.4 * height, 0));

    }

}
