package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    static Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
    private JNativeHookExample jNativeHookExample= new JNativeHookExample() {
        @Override
        public void hhh() {
            try {
                saveToBuff();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void jjj() {
            try {
                seveBufScreen();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            }
        }
    };

    private List<Image> images = new ArrayList<>();

    @FXML
    private ImageView mainImageView = new ImageView();
    @FXML
    private HBox imagesStore = new HBox(4);
    @FXML
    private   ScrollPane scrollPane = new ScrollPane();
    @FXML
    Button stopRun=new Button();

    private boolean isRun=false;

    public void stopRun() throws NativeHookException {
        if(!isRun) {
            stopRun.setText("Stop");
           // jNativeHookExample.runNative();
            runNative();
        }else {
            stopRun.setText("Run");
           // jNativeHookExample.stopNative();
            stopNative();
        }
        this.isRun=!isRun;
    }


    public void saveToBuff() throws IOException {
        System.out.println("saveToBuff");
        if(images.size()!=0) {
            BufferedImage imaget = null;
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(images.get(0), imaget);
            CopyImagetoClipBoard copyImagetoClipBoard = new CopyImagetoClipBoard(bufferedImage);
            deliteListItem();
        }

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        new Thread(() -> {
            String message="";
            String title="";
            if(images.size()==0){message="All images are pasted.";}
            if(images.size()!=0){message="Count of images in queue= "+images.size(); title="Image saved";}

            JOptionPane opt1 = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}); // no buttons
            final JDialog dlg2 = opt1.createDialog(title);
            dlg2.setLocation(width-300,height-150);

            new Thread(() -> {
                try {Thread.sleep(3000);
                    dlg2.dispose(); }
                catch ( Throwable th )
                {}
            }).start();

            dlg2.setVisible(true);
        }).start();

}

        public void deliteListItem(){
            ImageView imageView = new ImageView();
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(120);

            imageView.setImage(images.get(0));

            imagesStore.getChildren().remove(0);
            scrollPane.setContent(imagesStore);
            images.remove(0);

        }


        public void seveBufScreen() throws IOException, UnsupportedFlavorException {
            Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (content == null) {
                System.err.println("error: nothing found in clipboard");
            }else
                if (!content.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                    System.err.println("error: nothing found in clipboard");
                }else {
                 BufferedImage bufferedImage = (BufferedImage) content.getTransferData(DataFlavor.imageFlavor);
                 Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                 ImageView imageView = new ImageView();

                 imageView.setOnMouseClicked(event -> {
                            if (event.getButton() == MouseButton.PRIMARY) {
                                mainImageView.setImage(imageView.getImage());
                            }
                            if (event.getButton() == MouseButton.SECONDARY) {
                                imagesStore.getChildren().remove(imageView);
                                scrollPane.setContent(imagesStore);
                                images.remove(imageView.getImage());
                            }
                        }

                );


                imageView.setImage(image);

                imageView.setPreserveRatio(true);
                imageView.setFitHeight(120);

                imagesStore.getChildren().add(imageView);
                scrollPane.setContent(imagesStore);
                images.add(imageView.getImage());
            }
        }


    public void runn(){
        loadImages();
        this.mainImageView.setImage(images.get(0));
        this.mainImageView.setPreserveRatio(true);

        for (int i = 0; i < images.size(); ++i) {
            ImageView imageView = new ImageView();
            imageView.setOnMouseClicked(event ->{
                  if(event.getButton() == MouseButton.PRIMARY) {
                      mainImageView.setImage(imageView.getImage());
                  }
                  if(event.getButton() == MouseButton.SECONDARY) {
                           // mainImageView.setImage(imageView.getImage());

                      imagesStore.getChildren().remove(imageView);
                      scrollPane.setContent(imagesStore);
                      images.remove(imageView.getImage());

                        }
            }

            );
            imageView.setImage(images.get(i));

            imageView.setPreserveRatio(true);
            imageView.setFitHeight(120);
            imagesStore.getChildren().add(imageView);
        }
        this.scrollPane.setContent(imagesStore);
        this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void loadImages() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Directory");
        File directory = directoryChooser.showDialog(null);
        if (directory != null) {
            File[] files = directory.listFiles();
            String fileName;
            for (int i = 0; i < files.length; ++i) {
                fileName = files[i].getName().toLowerCase();
                if (files[i] != null && (fileName.endsWith(".jpg") || fileName.endsWith(".png") ||
                        fileName.endsWith(".bmp"))) {
                    try {
                        images.add(SwingFXUtils.toFXImage(ImageIO.read(files[i]), null));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.exit(0);
        }
    }




























    public void runNative(){
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new JNativeHookExample() {
            @Override
            public void hhh() {
                try {
                    saveToBuff();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void jjj() {
                try {
                    seveBufScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                }
            }
        });}

    public void stopNative() throws NativeHookException {
        GlobalScreen.unregisterNativeHook();
    }





}
