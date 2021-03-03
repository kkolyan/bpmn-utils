package com.nplekhanov.bpmn;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GenerateDiagram {
    public static void main(String[] args) throws IOException {
        String sourceFile = args[0];
        String targetFile = sourceFile+".png";
        BpmnModel model = new BpmnXMLConverter().convertToBpmnModel(() -> {
            try {
                return new FileInputStream(sourceFile);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }, true, false);


        DefaultProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
        FileUtils.copyInputStreamToFile(generator.generatePngDiagram(model), new File(targetFile));
    }
}
