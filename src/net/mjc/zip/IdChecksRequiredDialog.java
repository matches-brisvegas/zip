package net.mjc.zip;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IdChecksRequiredDialog extends AlertDialog {

    public interface Listener {
        public void onOkClick(IdChecksRequiredDialog dialog);
    }

    Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public IdChecksRequiredDialog(Context context, String title, String text) {

        super(context);
        final LayoutInflater li = LayoutInflater.from(context);
        View content = li.inflate(R.layout.idchecksrequired, null);
        setView(content);

        TextView textView = (TextView) content.findViewById(R.id.idChecksRequiredText);
        textView.setText(text);

        setTitle(title);

        final Button button = (Button) content.findViewById(R.id.idChecksRequiredTextOkButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onOkClick(IdChecksRequiredDialog.this);
            }
        });

    }
}