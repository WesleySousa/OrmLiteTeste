package br.com.example.ormliteteste.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.example.ormliteteste.R;
import br.com.example.ormliteteste.modelo.Pessoa;

/**
 * Created by wesley on 7/30/16.
 */
public class PessoaAdapter extends ArrayAdapter<Pessoa> {

    private Context context;
    private int resource;
    private List<Pessoa> pessoas;
    private LayoutInflater inflater;

    public PessoaAdapter(Context context, int resource, List<Pessoa> pessoas) {
        super(context, resource, pessoas);
        this.context = context;
        this.resource = resource;
        this.pessoas = pessoas;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
            holder = new Holder();
            holder.txtNome = (TextView) convertView.findViewById(R.id.txt_nome);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.txtNome.setText(pessoas.get(position).getNome());

        return convertView;
    }

    private class Holder {
        TextView txtNome;
    }
}
