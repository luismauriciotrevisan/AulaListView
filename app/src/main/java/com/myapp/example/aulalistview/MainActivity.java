package com.myapp.example.aulalistview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static String TAG = "AulaListView";
    String[]  nomePaises;
    TypedArray bandeiras;
    int[] qtd_array;// array que armazena quantidades
    boolean[] cb_array;//array que armazena estado dos check boxes
    ListView listView;//listView passou para escopo da classe MainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recupera a View da lista a ser utilizada
        //ListView listView = (ListView) findViewById(R.id.ListView01);
        listView = (ListView) findViewById(R.id.ListView01);

        //Recupera o contexto desta app
        Context ctx = getApplicationContext();
        //Recupera ref para resources
        Resources res = ctx.getResources();

        //restaura em nomePaises o array country_names
        nomePaises = res.getStringArray(R.array.country_names);
        //restaura em bandeiras o array de ids country_icons
        bandeiras = res.obtainTypedArray(R.array.country_icons);
        //instancia e inicializa array de quantidades
        qtd_array = new int[bandeiras.length()];
        for(int qtd:qtd_array){
            qtd = 0;
        }
        //instancia e inicializa o array para armazenar estado dos CheckBoxes
        cb_array = new boolean[bandeiras.length()];
        for(Boolean b:cb_array){

            b = false;
        }


        //O Adapter possui acesso a _todo conteúdo a ser exibido, cada ítem em cada linha da lista é acessado
        //somente através dele. Ele ADAPTA um conteúdo grande à uma janela bem menor!!!
        //Os métodos abaixo são apenas algumas callbacks que temos que criar para permitir que o Android gerencie
        //a ListView e recycle Views para nosso App!!! Tem muuuiiiito mais que isto ocorrendo fora dos holofotes ;)


        listView.setAdapter(new BaseAdapter(){
            //retorna o número de ítens total da lista
            public int getCount(){
                Log.d(TAG, "getCount");
                return nomePaises.length;
            }

            //retorna um item em específico, o nome de um país em dada posição da lista
            public Object getItem(int position){
                Log.d(TAG, "getItem");
                return nomePaises[position];
            }

            //retorna o Id de um ítem em dada posição
            //por opção de implementação o id é o mesmo que a posição
            //mas o Android e RecyclerView não sabem disso e nem devem presumí-lo
            public long getItemId(int position){
                Log.d(TAG, "getItemId");
                return position;
            }
            //retorna as Views que devem ser preenchidas quando a Janela exibir
            //determinada posição ou linha da Lista
            //getView será chamada assim que a tela for exeibida pela primeiríssima vez e a cada atualização
            public View getView(final int position, View convertView, ViewGroup parent){
                Log.d(TAG, "getView " + convertView + " " + parent);
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

                //recuperamos a TV de quantidade e setamos o valor correto
                TextView tv_qtd = (TextView)view.findViewById(R.id.tv_qtd);
                String quantidade = String.format("%d", qtd_array[position]);
                tv_qtd.setText(quantidade);

                //recuperamos a cb_01 e atulizamos seu estado
                CheckBox cb = (CheckBox) view.findViewById(R.id.cb_01);
                cb.setChecked(cb_array[position]);

                //recuperamos o botao de decremento
                Button bt_dec = (Button)view.findViewById(R.id.bt_dec);
                //setamos um listener para o botao de decremento
                bt_dec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //chama um método que trata o decremento e onde pode ser implementado outras funções
                        decClicked(position);
                        //força a atualização da tela, mas vc só irá entender se rodar com e sem esta linha
                        notifyDataSetChanged();
                    }
                });

                //recuperamos o botao de incremento
                Button bt_inc = (Button)view.findViewById(R.id.bt_inc);
                //setamos um listener para o botao de incremento
                bt_inc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //chama um método que trata o decremento e onde pode ser implementado outras funções
                        incClicked(position);
                        //força a atualização da tela, , mas vc só irá entender se rodar com e sem esta linha
                        notifyDataSetChanged();
                    }
                });

                return view;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick " + adapterView.getId());
                //Toast.makeText(MainActivity.this, nomePaises[i], Toast.LENGTH_SHORT).show();
                //invert o estado do CheckBox a cada click!
                cb_array[i] = !cb_array[i];
                //força a atualização da tela, , mas vc só irá entender se rodar com e sem esta linha
                ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
            }
        });
    }

    public void decClicked(int pos){
        qtd_array[pos]--;
        //aqui vc pode implementar a lógica de decrementar apenas se o checkbox estiver marcado
        //e ainda desmarcar o checkbox quando chegar em zero

    }

    public void incClicked(int pos){
        qtd_array[pos]++;
        //aqui vc pode implementar a lógica de incrementar apenas se o checkbox estiver marcado
    }
}
