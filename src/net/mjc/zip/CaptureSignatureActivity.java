package net.mjc.zip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class CaptureSignatureActivity extends Activity {

    private SignatureView signatureView;
    private LinearLayout content;
    private Button okBtn;

    private ActivityState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capturesignature);

        okBtn = (Button) findViewById(R.id.ok);
        okBtn.setEnabled(false);
        content = (LinearLayout) findViewById(R.id.signature);

        signatureView = new SignatureView(this, null);
        content.addView(signatureView);

        okBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.save();
            }
        });

        final Button clearBtn = (Button) findViewById(R.id.clear);
        clearBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clear();
            }
        });

        if (getIntent().getExtras() != null) {
            state = ActivityState.fromJson(getIntent().getExtras().getString(ActivityState.class.getName()));
            this.setTitle(state.getCurrentIdCheck().getDescription());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle b) {
        super.onCreate(b);
    }

    public class SignatureView extends View {
        static final float STROKE_WIDTH = 4f;
        static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        Paint paint = new Paint();
        Path path = new Path();

        float lastTouchX;
        float lastTouchY;
        final RectF dirtyRect = new RectF();

        public SignatureView(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void clear() {
            path.reset();
            invalidate();
            okBtn.setEnabled(false);
        }

        public void save() {

            Bitmap returnedBitmap = Bitmap.createBitmap(content.getWidth(), content.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(returnedBitmap);
            Drawable bgDrawable = content.getBackground();
            if (bgDrawable == null) {
                canvas.drawColor(Color.WHITE);
            } else {
                bgDrawable.draw(canvas);

            }
            content.draw(canvas);

            final ByteArrayOutputStream bs = new ByteArrayOutputStream();
            returnedBitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);

            final String name = "0-" + state.getCurrentIdCheck().getCode() + ".png";    //TODO
            final File file = new File(CaptureSignatureActivity.this.getFilesDir(), name);

            FileOutputStream outputStream;
            try {
                outputStream = new FileOutputStream(file);
                outputStream.write(bs.toByteArray());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent();
            setResult(1, intent);
            finish();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            okBtn.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }
}
