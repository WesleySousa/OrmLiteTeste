package br.com.example.ormliteteste.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import br.com.example.ormliteteste.R;
import br.com.example.ormliteteste.modelo.Conta;

/**
 * Created by wesley on 7/30/16.
 */
public class ContaAdapter extends ArrayAdapter<Conta> {

    private Context context;
    private int resource;
    private List<Conta> contas;
    private LayoutInflater inflater;

    public ContaAdapter(Context context, int resource, List<Conta> contas) {
        super(context, resource, contas);
        this.context = context;
        this.resource = resource;
        this.contas = contas;
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

        StringBuilder texto = new StringBuilder();
        texto.append(contas.get(position).getAgencia());
        texto.append(" - ");
        texto.append(contas.get(position).getNumero());
        texto.append(" - ");
        if (contas.get(position).getSaldo() != null)
            texto.append(String.format(Locale.getDefault(), "RS %.2f", contas.get(position).getSaldo()));
        else
            texto.append("RS 0.00");

        holder.txtNome.setText(texto.toString());

        return convertView;
    }

    private class Holder {
        TextView txtNome;
    }
}
