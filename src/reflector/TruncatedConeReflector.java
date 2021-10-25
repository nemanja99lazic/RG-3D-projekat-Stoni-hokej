package reflector;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class TruncatedConeReflector extends Group {

    public static final int DIV = 100;
    private PointLight light;

    public TruncatedConeReflector(double r, double H, double R)
    {
        float[] points = new float[2 * DIV * 3 + 3]; // vrati na 2 * DIV * 3 + 3

        int centerX = 2 * DIV * 3;

        int indCenter = 2 * DIV;

        points[centerX + 0] = 0f;
        points[centerX + 1] = 0f;
        points[centerX + 2] = 0f;

        double anglePart = 2 * Math.PI / DIV;

        for(int i = 0; i < DIV; i++)
        {
            points[i * 3 + 0] = (float) (r * Math.cos(anglePart * i)); // top X coord
            points[i * 3 + 1] = 0f;                                              // top Y coord
            points[i * 3 + 2] = (float) (r * Math.sin(anglePart * i)); // top Z coord

            points[DIV * 3 + i * 3 + 0] = (float) (R * Math.cos(anglePart * i)); // bottom X coord
            points[DIV * 3 + i * 3 + 1] = (float)H;                                               // bottom Y coord;
            points[DIV * 3 + i * 3 + 2] = (float) (R * Math.sin(anglePart * i)); // bottom Z coord;
        }

        float[] textCoords = {
                0.5f, 0,
                0, 1,
                1, 1
        };

        int[] faces = new int[2 * 6 * 3 * DIV]; // vrati na 2 * 6 * 3 * DIV


        for(int i = 0; i < DIV; i++)
        {
            // front face, top circle
            faces[i * 12 + 0] = indCenter;
            faces[i * 12 + 1] = 0;

            faces[i * 12 + 2] = i;
            faces[i * 12 + 3] = 1;

            if((i + 1) % DIV == 0)
                faces[i * 12 + 4] = 1;
            else
                faces[i * 12 + 4] = i + 1;
            faces[i * 12 + 5] = 2;

            //back face, top circle
            faces[i * 12 + 6] = indCenter;
            faces[i * 12 + 7] = 0;

            if((i + 1) % DIV == 0)
                faces[i * 12 + 8] = 1;
            else
                faces[i * 12 + 8] = i + 1;
            faces[i * 12 + 9] = 2;

            faces[i * 12 + 10] = i;
            faces[i * 12 + 11] = 1;
        }

        for(int i = 0; i < DIV; i++)
        {
            // mantle top down, front face

            faces[(DIV + i) * 12 + 0] = (i + 1) % DIV;
            faces[(DIV + i) * 12 + 1] = 0;

            faces[(DIV + i) * 12 + 2] = i;
            faces[(DIV + i) * 12 + 3] = 1;

            faces[(DIV + i) * 12 + 4] = DIV + i;
            faces[(DIV + i) * 12 + 5] = 2;

            // mantle top down, back face
            faces[(DIV + i) * 12 + 6] = (i + 1) % DIV;
            faces[(DIV + i) * 12 + 7] = 0;

            faces[(DIV + i) * 12 + 8] = DIV + i;
            faces[(DIV + i) * 12 + 9] = 2;

            faces[(DIV + i) * 12 + 10] = i;
            faces[(DIV + i) * 12 + 11] = 1;
        }

        for(int i = 0; i < DIV; i++)
        {
            // mantle down top, front face
            faces[(2 * DIV + i) * 12 + 0] = i;
            faces[(2 * DIV + i) * 12 + 1] = 0;

            faces[(2 * DIV + i) * 12 + 2] = DIV + i;
            faces[(2 * DIV + i) * 12 + 3] = 1;

            if(i - 1 >= 0)
                faces[(2 * DIV + i) * 12 + 4] = DIV + i - 1;
            else
                faces[(2 * DIV + i) * 12 + 4] = DIV + DIV - 1;
            faces[(2 * DIV + i) * 12 + 5] = 2;

            // mantle down top, back face
            faces[(2 * DIV + i) * 12 + 6] = i;
            faces[(2 * DIV + i) * 12 + 7] = 0;

            if(i - 1 >= 0)
                faces[(2 * DIV + i) * 12 + 8] = DIV + i - 1;
            else
                faces[(2 * DIV + i) * 12 + 8] = DIV + DIV - 1;
            faces[(2 * DIV + i) * 12 + 9] = 2;

            faces[(2 * DIV + i) * 12 + 10] = DIV + i;
            faces[(2 * DIV + i) * 12 + 11] = 1;
        }

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(points);
        mesh.getTexCoords().addAll(textCoords);
        mesh.getFaces().addAll(faces);

        MeshView meshView = new MeshView();
        meshView.setMesh(mesh);

        PhongMaterial material = new PhongMaterial(Color.GRAY);
        meshView.setMaterial(material);

        //meshView.setDrawMode(DrawMode.LINE);

        this.light = new PointLight(Color.WHITE);

        this.getChildren().addAll(meshView, this.light);
    }

    public void switchLight() {
        if(this.light.isLightOn())
            this.light.setLightOn(false);
        else
            this.light.setLightOn(true);
    }
}
