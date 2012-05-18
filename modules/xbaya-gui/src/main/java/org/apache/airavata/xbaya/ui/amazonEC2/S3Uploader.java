/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.apache.airavata.xbaya.ui.amazonEC2;

import java.io.File;

import javax.swing.JDialog;

import org.apache.airavata.xbaya.XBayaEngine;
import org.apache.airavata.xbaya.ui.Cancelable;
import org.apache.airavata.xbaya.ui.WaitDialog;
import org.jets3t.service.S3Service;
import org.jets3t.service.model.S3Object;

public class S3Uploader implements Cancelable {
    private XBayaEngine engine;
    private JDialog parent;

    private boolean canceled;

    private WaitDialog loadingDialog;

    /**
     * Constructs a S3Uploader.
     * 
     * @param engine
     * @param parent
     */
    public S3Uploader(XBayaEngine engine, JDialog parent) {
        this.engine = engine;
        this.parent = parent;
        this.loadingDialog = new WaitDialog(this, "Uploading file to S3.", "Uploading file to S3.\n"
                + "Please wait for a moment.", this.engine);
    }

    /**
     * @see org.apache.airavata.xbaya.ui.Cancelable#cancel()
     */
    @Override
    public void cancel() {
        this.canceled = true;
    }

    /**
     * 
     * @param s3
     * @param s3tree
     * @param bucket
     * @param filePath
     */
    public void upload(final S3Service s3, final S3Tree s3tree, final String bucket, final String filePath) {

        new Thread(new Runnable() {

            @Override
            public void run() {

                int index;
                index = filePath.lastIndexOf('/');
                String fileName;
                if (index == -1) {
                    index = filePath.lastIndexOf('\\');
                }
                fileName = filePath.substring(index + 1, filePath.length());
                try {
                    S3Object s3Object = new S3Object(new File(filePath));
                    s3.putObject(bucket, s3Object);

                    /*
                     * We cannot cancel during upload, so delete file instead
                     */
                    if (S3Uploader.this.canceled) {
                        s3.deleteObject(bucket, s3Object.getKey());
                    } else {

                        S3Uploader.this.engine.getErrorWindow().info(S3Uploader.this.parent, "",
                                "Uploaded successfully!");

                        // add key to S3Tree
                        String uploadString = bucket;
                        int startIndex = uploadString.lastIndexOf('/');
                        startIndex = startIndex >= 0 ? startIndex : 0;
                        if (startIndex != 0) {
                            fileName = uploadString.substring(startIndex) + '/' + fileName;
                        }

                        if (fileName.startsWith("/")) {
                            fileName = fileName.substring(1, fileName.length());
                        }

                        s3tree.addObject(bucket, fileName);
                    }

                } catch (Exception ex) {
                    S3Uploader.this.engine.getErrorWindow().error(S3Uploader.this.parent,
                            "Upload failed! Please ensure every fields are filled correctly", ex);
                } finally {
                    // close loading dialog
                    S3Uploader.this.loadingDialog.hide();
                }
            }

        }).start();

        this.loadingDialog.show();
    }

}