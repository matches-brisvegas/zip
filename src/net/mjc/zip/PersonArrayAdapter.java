package net.mjc.zip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import net.mjc.zip.domain.Person;

/**
 * Created by matt on 1/02/2015.
 */
public class PersonArrayAdapter extends ArrayAdapter<Person> {
    private final Context context;
    private final Person[] ppl;

    public PersonArrayAdapter(Context context, Person[] ppl) {
        super(context, R.layout.ppl, ppl);
        this.context = context;
        this.ppl = ppl;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.ppl, parent, false);

        Person person = ppl[position];

        TextView textView = (TextView) rowView.findViewById(R.id.description);
        textView.setText(person.getFirstName() + " " + person.getLastName() + ". Matter: " + person.getMatterId());

        ImageView imageView = (ImageView) rowView.findViewById(R.id.avatar);
        if (Person.Sex.Male.equals(person.getSex())) {
            imageView.setImageResource(R.drawable.male);
        } else {
            imageView.setImageResource(R.drawable.female);
        }
        return rowView;
    }
}

