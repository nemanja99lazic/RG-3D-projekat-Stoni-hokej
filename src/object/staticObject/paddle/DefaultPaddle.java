package object.staticObject.paddle;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Translate;

public class DefaultPaddle extends Paddle{

    public DefaultPaddle ( double width, double height, double depth ) {
        super(width, height, depth);

        Box base = new Box(0.4 * width, height, depth);
        PhongMaterial paddleMaterial = new PhongMaterial(Color.GREEN);
        base.setMaterial(paddleMaterial);
        base.getTransforms().addAll(
                new Translate(0, -0.3 * height, 0)
        );
        super.getChildren().addAll(base);

        float w = (float) width;
        float h = (float) height;
        float d = (float) depth;

        float[] firstPoints = {
                0.2f * w, 0.2f * h, -0.5f * d,
                0.5f * w, 0.2f * h, -0.5f * d,
                0.2f * w, -0.2f * h, -0.5f * d,
                0.2f * w, 0.2f * h, 0.5f * d,
                0.5f * w, 0.2f * h, 0.5f * d,
                0.2f * w, -0.2f * h, 0.5f * d,
        };

        float firstTexCoord[] = {
                0f, 0f
        };

        int firstFaces[] = {
                0, 0, 1, 0, 2, 0,
                0, 0, 2, 0, 1, 0,
                5, 0, 1, 0, 2, 0,
                5, 0, 2, 0, 1, 0,
                5, 0, 4, 0, 1, 0,
                5, 0, 1, 0, 4, 0,
                5, 0, 4, 0, 3, 0,
                5, 0, 3, 0, 4, 0,
                4, 0, 1, 0, 0, 0,
                4, 0, 0, 0, 1, 0,
                4, 0, 0, 0, 3, 0,
                4, 0, 3, 0, 0, 0
        };

        TriangleMesh firstTriangleMesh = new TriangleMesh();
        firstTriangleMesh.getPoints().addAll(firstPoints);
        firstTriangleMesh.getTexCoords().addAll(firstTexCoord);
        firstTriangleMesh.getFaces().addAll(firstFaces);

        MeshView firstMeshView = new MeshView(firstTriangleMesh);
        firstMeshView.setMaterial(paddleMaterial);

        float[] secondPoints = {
                -0.2f * w, 0.2f * h, -0.5f * d,
                -0.5f * w, 0.2f * h, -0.5f * d,
                -0.2f * w, -0.2f * h, -0.5f * d,
                -0.2f * w, 0.2f * h, 0.5f * d,
                -0.5f * w, 0.2f * h, 0.5f * d,
                -0.2f * w, -0.2f * h, 0.5f * d,
        };

        float secondTexCoord[] = {
                0f, 0f
        };

        int secondFaces[] = {
                0, 0, 1, 0, 2, 0,
                0, 0, 2, 0, 1, 0,
                5, 0, 1, 0, 2, 0,
                5, 0, 2, 0, 1, 0,
                5, 0, 4, 0, 1, 0,
                5, 0, 1, 0, 4, 0,
                5, 0, 4, 0, 3, 0,
                5, 0, 3, 0, 4, 0,
                4, 0, 1, 0, 0, 0,
                4, 0, 0, 0, 1, 0,
                4, 0, 0, 0, 3, 0,
                4, 0, 3, 0, 0, 0
        };

        TriangleMesh secondTriangleMesh = new TriangleMesh();
        secondTriangleMesh.getPoints().addAll(secondPoints);
        secondTriangleMesh.getTexCoords().addAll(secondTexCoord);
        secondTriangleMesh.getFaces().addAll(secondFaces);

        MeshView secondMeshView = new MeshView(secondTriangleMesh);
        secondMeshView.setMaterial(paddleMaterial);

        super.getChildren().addAll(firstMeshView, secondMeshView);
    }

}
