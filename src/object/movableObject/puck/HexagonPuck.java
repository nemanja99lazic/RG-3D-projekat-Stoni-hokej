package object.movableObject.puck;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

import java.awt.*;

public class HexagonPuck extends Puck{

    public HexagonPuck(double radius, double height)
    {
        super(radius, height);

        double dAngle = 2 * Math.PI / 6;

        float[] points = new float[(6 + 6 + 2) * 3];

        float[] texCoords = new float[]{
            0.5f, 0f,
            0f, 1f,
            1f, 1f
        };

        int[] faces = new int[(6 + 6 + 6 * 2) * 6 * 2];

        int centerUpCoord = (6 + 6) * 3;
        int centerDownCoord = (6 + 6 + 1) * 3;

        int centerUpInd = 6 + 6;
        int centerDownInd = 6 + 6 + 1;

        points[centerUpCoord + 0] = 0;
        points[centerUpCoord + 1] = - (float) height / 2;
        points[centerUpCoord + 2] = 0;

        points[centerDownCoord + 0] = 0;
        points[centerDownCoord + 1] = (float)height / 2;
        points[centerDownCoord + 2] = 0;

        for(int i = 0; i < 6; i++)
        {
            // up
            points[i * 3 + 0] = (float) (radius * Math.cos(i * dAngle));
            points[i * 3 + 1] = - (float)(height / 2);
            points[i * 3 + 2] = (float) (radius * Math.sin(i * dAngle));

            // down
            points[6 * 3 + i * 3 + 0] = (float) (radius * Math.cos(i * dAngle));
            points[6 * 3 + i * 3 + 1] = (float)(height / 2);
            points[6 * 3 + i * 3 + 2] = (float) (radius * Math.sin(i * dAngle));
        }

        // up faces
        for(int i = 0; i < 6; i++)
        {
            faces[i * 12 + 0] = centerUpInd;
            faces[i * 12 + 1] = 0;

            faces[i * 12 + 2] = i;
            faces[i * 12 + 3] = 1;

            faces[i * 12 + 4] = (i + 1) % 6;
            faces[i * 12 + 5] = 2;

            faces[i * 12 + 6] = centerUpInd;
            faces[i * 12 + 7] = 0;

            faces[i * 12 + 8] = (i + 1) % 6;
            faces[i * 12 + 9] = 2;

            faces[i * 12 + 10] = i;
            faces[i * 12 + 11] = 1;
        }

        // down faces
        for(int i = 0; i < 6; i++)
        {
            faces[6 * 12 + i * 12 + 0] = centerDownInd;
            faces[6 * 12 + i * 12 + 1] = 0;

            faces[6 * 12 + i * 12 + 2] = 6 + (i + 1) % 6;
            faces[6 * 12 + i * 12 + 3] = 1;

            faces[6 * 12 + i * 12 + 4] = 6 + i;
            faces[6 * 12 + i * 12 + 5] = 2;

            faces[6 * 12 + i * 12 + 6] = centerDownInd;
            faces[6 * 12 + i * 12 + 7] = 0;

            faces[6 * 12 + i * 12 + 8] = 6 + i;
            faces[6 * 12 + i * 12 + 9] = 2;

            faces[6 * 12 + i * 12 + 10] = 6 + (i + 1) % 6;
            faces[6 * 12 + i * 12 + 11] = 1;
        }

        // side faces top down
        for(int i = 0; i < 6; i++)
        {
            faces[2 * 6 * 12 + i * 6 + 0] = i;
            faces[2 * 6 * 12 + i * 6 + 1] = 0;

            faces[2 * 6 * 12 + i * 6 + 2] = 6 + i;
            faces[2 * 6 * 12 + i * 6 + 3] = 1;

            faces[2 * 6 * 12 + i * 6 + 4] = 6 + (i + 1) % 6;
            faces[2 * 6 * 12 + i * 6 + 5] = 2;

            faces[2 * 6 * 12 + i * 6 + 6] = i;
            faces[2 * 6 * 12 + i * 6 + 7] = 0;

            faces[2 * 6 * 12 + i * 6 + 8] = 6 + (i + 1) % 6;
            faces[2 * 6 * 12 + i * 6 + 9] = 2;

            faces[2 * 6 * 12 + i * 6 + 10] = 6 + i;
            faces[2 * 6 * 12 + i * 6 + 11] = 1;
        }

        // side faces - down top
        for(int i = 0; i < 6; i++)
        {
            faces[3 * 6 * 12 + i * 6 + 0] = 6 + i;
            faces[3 * 6 * 12 + i * 6 + 1] = 0;

            faces[3 * 6 * 12 + i * 6 + 2] = i;
            faces[3 * 6 * 12 + i * 6 + 3] = 1;

            faces[3 * 6 * 12 + i * 6 + 4] = i - 1 < 0 ? 5 :(i - 1);
            faces[3 * 6 * 12 + i * 6 + 5] = 2;

            faces[3 * 6 * 12 + i * 6 + 6] = 6 + i;
            faces[3 * 6 * 12 + i * 6 + 7] = 0;

            faces[3 * 6 * 12 + i * 6 + 8] = i - 1 < 0 ? 5 :(i - 1);
            faces[3 * 6 * 12 + i * 6 + 9] = 2;

            faces[3 * 6 * 12 + i * 6 + 10] = i;
            faces[3 * 6 * 12 + i * 6 + 11] = 1;
        }

        TriangleMesh triangleMesh = new TriangleMesh();
        triangleMesh.getPoints().addAll(points);
        triangleMesh.getTexCoords().addAll(texCoords);
        triangleMesh.getFaces().addAll(faces);

        MeshView meshView = new MeshView();
        meshView.setMesh(triangleMesh);

        //meshView.setDrawMode(DrawMode.LINE);

        PhongMaterial material = new PhongMaterial(Color.BLUE);
        meshView.setMaterial(material);
        super.getChildren().add(meshView);
    }
}
