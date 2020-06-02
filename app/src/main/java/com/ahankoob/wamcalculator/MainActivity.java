package com.ahankoob.wamcalculator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahankoob.wamcalculator.tools.MyBounceInterpolator;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
	ArrayList<HomeNav> homeNavs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		homeNavs = getHomeNavs();
		drawGrid();

	}
	public ArrayList<HomeNav> getHomeNavs(){
		ArrayList<HomeNav> homeNavs = new ArrayList<HomeNav>();
		homeNavs.add(new HomeNav(1,"اقساط وام بدون مبلغ مسدودی",R.drawable.budgeting));
		homeNavs.add(new HomeNav(2,"اقساط وام با مبلغ مسدودی",R.drawable.budgeting));
		homeNavs.add(new HomeNav(3,"محاسبه سهم اصل و سود اقساط وام",R.drawable.budget1));
		homeNavs.add(new HomeNav(4,"محاسبه سود سپرده",R.drawable.savemoney));
		homeNavs.add(new HomeNav(5,"تسویه پیش از سررسید وام (به زودی)",R.drawable.return_money));

		return homeNavs;
	}
	public void drawGrid(){

		GridView gridView = (GridView) findViewById(R.id.banksListGridView);
		gridView.setAdapter(new GridViewAdapter(this,homeNavs));





	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem aboutmenu =  menu.add("درباره محصول");
		aboutmenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("bazaar://details?id=" + "com.ahankoob.wamcalculator"));
				intent.setPackage("com.farsitel.bazaar");
				startActivity(intent);
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);

	}
}
class GridViewAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<HomeNav> homeNavs;
	public GridViewAdapter(Context context,ArrayList<HomeNav> homeNavs) {
		this.context = context;
		this.homeNavs=homeNavs;
	}
	@Override
	public int getCount() {
		return this.homeNavs.size();
	}
	@Override
	public Object getItem(int position) {
		return homeNavs.get(position);
	}
	@Override
	public long getItemId(int position) {
		return homeNavs.get(position).getId();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final HomeNav item = homeNavs.get(position);
		View Layout;

		if (convertView == null) {
			LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			Layout = inflater.inflate(R.layout.layout_home_nav_list_item,null);
			ImageView homeNavIcon = (ImageView) Layout.findViewById(R.id.homeNavIcon);
			TextView homeNavTitle  = (TextView) Layout.findViewById(R.id.homeNavTitle);

			FontManager.markAsIconContainer(homeNavIcon, FontManager.getfontawesomeFont(context.getAssets()));
			FontManager.markAsIconContainer(homeNavTitle, FontManager.getVazirFont(context.getAssets()));
			final LinearLayout button = (LinearLayout) Layout.findViewById(R.id.banksLinearLAyout);
			homeNavIcon.setImageDrawable(context.getResources().getDrawable(item.getIcon()));
			homeNavTitle.setText(item.getTitle());
			Layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
					MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
					myAnim.setInterpolator(interpolator);
					button.startAnimation(myAnim);
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							if(item.getId()==1){
								context.startActivity(new Intent(context,NewCalculatorActivity.class));
							}
							else if(item.getId()==2){
								context.startActivity(new Intent(context,MasdoodCalculatorActivity.class));
							}
							else if(item.getId()==3){
								context.startActivity(new Intent(context,AslSoodJadvalActivity.class));
							}
							else if(item.getId()==4){
								context.startActivity(new Intent(context,SoodSeporde.class));
							}
						}
					}, 350);

					//Intent calIntent = new Intent(context,CalculatorActivity.class);
					//calIntent.putExtra("bankID",item.getId());
					//context.startActivity(calIntent);
				}
			});
		} else {
			Layout = (View) convertView;
		}

		return Layout;
	}

}

