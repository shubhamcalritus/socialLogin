package shubham.jain.space.splashlib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Shubham Jain on 27/4/17 2:11 PM
 */

public class My_Splash extends LinearLayout {
    ImageView imageView;
    TimeExecuteListener timeExecuteListener;

    /**
     * default constructor
     *
     * @param context for initializing the layout
     */
    public My_Splash(Context context) {
        super(context);
        initialize(context);
    }

    /**
     * @param context for initializing the layout
     * @param attrs   for providing extra attribute
     */
    public My_Splash(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    /**
     * setTime executor to execute task after define timeline
     *
     * @param timeExecuteListener is listener to listen the time out event
     * @param time                negative value for never execute positive for execute after define millisecond
     */
    public void setTimeExecuteListener(final TimeExecuteListener timeExecuteListener, long time) {
        this.timeExecuteListener = timeExecuteListener;
        if (time > 0) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    // this code will be executed after time millisecond
                    timeExecuteListener.onExecute();
                }
            }, time);
        }
    }

    /**
     * initialize the component of the view
     *
     * @param context for initializing the layout
     */
    private void initialize(Context context) {
        View view = inflate(context, R.layout.my_splash, this);
        imageView = (ImageView) view.findViewById(R.id.splashImage);
    }

    /**
     * set Image in the view
     *
     * @param img_id is the reference id of image
     */
    public void setImageInImageView(int img_id) {
        imageView.setImageResource(img_id);

    }
}
