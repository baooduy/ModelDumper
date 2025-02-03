package dev.tr7zw.modeldumper.exporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.VertexConsumer;

public class ObjConsumer implements VertexConsumer {

    private float x,y,z;
    private float u,v;
    private List<VertexData> vertexData = new ArrayList<>();
    
    @Override
    public VertexConsumer setColor(int i, int j, int k, int l) {
        return this;
    }
	
    public void defaultColor(int i, int j, int k, int l) {
    }

    public void endVertex() {
        vertexData.add(new VertexData(x, y, z, u, v));
    }

    @Override
    public VertexConsumer setNormal(float f, float g, float h) {
        return this;
    }

    public VertexConsumer overlayCoords(int i, int j) {
        return this;
    }

    public void unsetDefaultColor() {

    }

    @Override
    public VertexConsumer setUv(float f, float g) {
        this.u = f;
        this.v = g;
		endVertex();
        return this;
    }

    @Override
    public VertexConsumer setUv1(int i, int j) {
        return this;
    }
	
    @Override
    public VertexConsumer setUv2(int i, int j) {
        return this;
    }

    @Override
    public VertexConsumer addVertex(float d, float e, float f) {
        this.x = d;
        this.y = e;
        this.z = f;
        return this;
    }
    
    public void writeData(File location) throws IOException {
        if(location.exists()) {
            location.delete();
        }
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(location)))){
            for(VertexData vertex : vertexData) {
                out.write("v " + vertex.x + " " + vertex.y + " " + vertex.z + "\n");
            }
            for(VertexData vertex : vertexData) {
                out.write("vt " + vertex.u + " " + (1f-vertex.v) + "\n");
            }
            for(int i = 1; i <= vertexData.size(); i+=4) {
                out.write("f " + i + "/" + i + " " + (i+1) + "/"+ (i+1) + " " + (i+2) + "/"+ (i+2) +" " + (i+3) + "/"+ (i+3) +"\n");
            }
        }
    }
    
    private record VertexData(float x, float y, float z, float u, float v) {}

}
