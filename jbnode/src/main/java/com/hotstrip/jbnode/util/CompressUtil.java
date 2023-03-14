package com.hotstrip.jbnode.util;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.*;
import java.nio.file.Paths;

public final class CompressUtil {

    public static void decompressTarGz(String inputFilePath, String outputDirectory) throws Exception {
        try (TarArchiveInputStream tarIn = new TarArchiveInputStream(new GzipCompressorInputStream(new FileInputStream(inputFilePath)))) {
            TarArchiveEntry tarEntry = tarIn.getNextTarEntry();
            while (tarEntry != null) {
                File outputFile = Paths.get(outputDirectory, tarEntry.getName()).toFile();
                if (tarEntry.isDirectory()) {
                    outputFile.mkdirs();
                } else {
                    outputFile.getParentFile().mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                        copyTarArchiveEntry(tarIn, fos);
                    }
                }
                tarEntry = tarIn.getNextTarEntry();
            }
        }
    }

    private static void copyTarArchiveEntry(TarArchiveInputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
}
