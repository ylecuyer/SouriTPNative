package fr.ylecuyer.souritp.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import fr.ylecuyer.souritp.DAO.Terminus;
import fr.ylecuyer.souritp.DAO.Type;

@EViewGroup(android.R.layout.simple_spinner_dropdown_item)
public class TypeSpinnerItemView extends LinearLayout {

    @ViewById(android.R.id.text1)
    TextView typeName;

    public TypeSpinnerItemView(Context context) {
        super(context);
    }

    public void bind(Type type) {
        typeName.setText(type.getName());
    }

}
