package sample;

import javafx.application.Platform;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class JNativeHookExample implements NativeKeyListener {
    static Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
    private boolean alt = false;
    private boolean letterV = false;

    public void nativeKeyPressed(NativeKeyEvent e) {

        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R) {
            this.alt = true;
        } else if (e.getKeyCode() == NativeKeyEvent.VC_V) {
            this.letterV = true;

            Platform.runLater( () -> {
                if (this.alt && this.letterV) {
                    System.out.println("ALT + N Pressed");
                    hhh();
                }
            });

        }

//        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
//            try {
//                GlobalScreen.unregisterNativeHook();
//            } catch (NativeHookException ex) {
//                Logger.getLogger(JNativeHookExample.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {

        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()) + this.alt + this.letterV);

        if (e.getKeyCode() == NativeKeyEvent.VC_SHIFT_L) {
            this.alt = false;
        }

//        Platform.runLater( () -> {
//            if (this.alt && this.letterV) {
//            System.out.println("ALT + N Pressed");
//            hhh();
//
//        }
//            System.out.println("ffff5555"+ this.alt+" "+this.letterV);
//
//        });

//        if (this.alt && this.letterN) {
//            System.out.println("ALT + N Pressed 0");
//        }


        if (e.getKeyCode() == NativeKeyEvent.VC_V) {
            System.out.println(4);
            this.letterV = false;
        }

        Platform.runLater( () -> {
            if (e.getKeyCode() == NativeKeyEvent.VC_PRINTSCREEN) {
                System.out.println("Print Screen");
                jjj();
            }
        });


//        if (e.getKeyCode() == NativeKeyEvent.VC_PRINTSCREEN) {
//            System.out.println("Print Screen");
//            jjj();
//        }

    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

//    public void runNative(){logger.setLevel(Level.WARNING);
//        logger.setUseParentHandlers(false);
//        try {
//            GlobalScreen.registerNativeHook();
//        } catch (NativeHookException ex) {
//            System.err.println("There was a problem registering the native hook.");
//            System.err.println(ex.getMessage());
//            System.exit(1);
//        }
//
//        GlobalScreen.addNativeKeyListener(new JNativeHookExample() {
//            @Override
//            public void hhh() {
//                System.out.println("hhhp6");
//            }
//        });}
//
//    public void stopNative() throws NativeHookException {
//        GlobalScreen.unregisterNativeHook();
//    }

    public  abstract void hhh();
    public abstract void jjj();



}