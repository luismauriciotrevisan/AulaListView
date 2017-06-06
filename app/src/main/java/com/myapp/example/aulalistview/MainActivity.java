package com.myapp.example.aulalistview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String[]  nomePaises;
    TypedArray bandeiras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recupera a View da lista a ser utilizada
        ListView listView = (ListView) findViewById(R.id.ListView01);

        //Recupera o contexto desta app
        Context ctx = getApplicationContext();
        //Recupera ref para resources
        Resources res = ctx.getResources();

        nomePaises = res.getStringArray(R.array.country_names);
        bandeiras = res.obtainTypedArray(R.array.country_icons);

        //O Adapter possui acesso a _todo conteúdo a ser exibido, cada ítem em cada linha da lista é acessado
        //somente através dele. Ele ADAPTA um conteúdo grande à uma janela bem menor!!!
        //Os métodos abaixo são apenas algumas callbacks que temos que criar para permitir que o Android gerencie
        //a ListView e recycle Views para nosso App!!! Tem muuuiiiito mais que isto ocorrendo fora dos holofotes ;)
        //O RecyclerView é gerenciado pelo próprio Android e se comunica diretamente com o Adapter setado para a ListView.

        listView.setAdapter(new BaseAdapter(){
            //retorna o número de ítens total da lista
            public int getCount(){
                return nomePaises.length;
            }
            //retorna um item em específico, o nome de um país em dada posição da lista
            public Object getItem(int position){
                return nomePaises[position];
            }
            //retorna o Id de um ítem em dada posição
            //por opção de implementação o id é o mesmo que a posição
            //mas o Android e RecyclerView não sabem disso e nem devem presumí-lo
            public long getItemId(int position){
                return position;
            }
            //retorna as Views que devem ser preenchidas quando a Janela exibir
            //determinada posição ou linha da Lista
            public View getView(int position, View convertView, ViewGroup parent){
                //recuperamos o serviço de Inflate do Android porque precisamos dele para carregar nosso Layout
                // dinamicamente
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                //de posse do inflate podemos inflar a Vew do List_row que corresponde há uma linha da tabela
                View view = inflater.inflate(R.layout.list_row, null);

                //recuperamos o TV e setamos o valor correto
                TextView textView = (TextView) view.findViewById(R.id.tv_01_view);
                textView.setText(nomePaises[position]);

                //recumeramos a iv e setamos a imagem correta
                ImageView iv = (ImageView) view.findViewById(R.id.img_01_view);
                iv.setImageDrawable(bandeiras.getDrawable(position));
                return view;
            }

        });
    }
}
