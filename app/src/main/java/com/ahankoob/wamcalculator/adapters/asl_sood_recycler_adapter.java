package com.ahankoob.wamcalculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahankoob.wamcalculator.FontManager;
import com.ahankoob.wamcalculator.NumberTextWatcherForThousand;
import com.ahankoob.wamcalculator.R;
import com.ahankoob.wamcalculator.models.ghest;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by abolfazl on 4/29/2020.
 */

public class asl_sood_recycler_adapter extends RecyclerView.Adapter<asl_sood_recycler_adapter.ViewHolder> {
	private Context context;
	private ArrayList<ghest> items;

	public asl_sood_recycler_adapter(Context context, ArrayList<ghest> items){
		this.items = items;
		this.context = context;
	}
	public class ViewHolder extends RecyclerView.ViewHolder{

		public TextView ghestNumber,AslSoodGhestTitle,AslSoodGhestMablagh,AslSoodAslTitle,AslSoodAslMablagh,AslSoodSoodTitle,AslSoodSoodMablagh;
		public ViewHolder(View itemView) {
			super(itemView);
			ghestNumber = (TextView) itemView.findViewById(R.id.ghestNumber);
			AslSoodGhestMablagh = (TextView) itemView.findViewById(R.id.AslSoodGhestMablagh);
			AslSoodAslMablagh = (TextView) itemView.findViewById(R.id.AslSoodAslMablagh);
			AslSoodSoodMablagh = (TextView) itemView.findViewById(R.id.AslSoodSoodMablagh);

			AslSoodGhestTitle = (TextView) itemView.findViewById(R.id.AslSoodGhestTitle);
			AslSoodAslTitle = (TextView) itemView.findViewById(R.id.AslSoodAslTitle);
			AslSoodSoodTitle = (TextView) itemView.findViewById(R.id.AslSoodSoodTitle);

			setfonts();

		}
		public void setfonts(){
			FontManager.markAsIconContainer(ghestNumber, FontManager.getVazirFont(itemView.getContext().getAssets()));
			FontManager.markAsIconContainer(AslSoodGhestMablagh, FontManager.getVazirFont(itemView.getContext().getAssets()));
			FontManager.markAsIconContainer(AslSoodAslMablagh, FontManager.getVazirFont(itemView.getContext().getAssets()));
			FontManager.markAsIconContainer(AslSoodSoodMablagh, FontManager.getVazirFont(itemView.getContext().getAssets()));

			FontManager.markAsIconContainer(AslSoodGhestTitle, FontManager.getVazirFont(itemView.getContext().getAssets()));
			FontManager.markAsIconContainer(AslSoodAslTitle, FontManager.getVazirFont(itemView.getContext().getAssets()));
			FontManager.markAsIconContainer(AslSoodSoodTitle, FontManager.getVazirFont(itemView.getContext().getAssets()));

		}

	}
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.asl_sood_jadval_item,viewGroup,false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder Holder, int i) {
		final ghest item = items.get(i);
		Holder.ghestNumber.setText(String.valueOf(item.getGhestNumber()));
		Holder.AslSoodGhestMablagh.setText(String.valueOf(Integer.valueOf(item.getGhest().intValue())));
		Holder.AslSoodAslMablagh.setText(String.valueOf(Integer.valueOf(item.getAsl().intValue())));
		Holder.AslSoodSoodMablagh.setText(String.valueOf(Integer.valueOf(item.getSood().intValue())));

		Holder.AslSoodGhestMablagh.setText(NumberTextWatcherForThousand.getDecimalFormattedString((String) Holder.AslSoodGhestMablagh.getText()));
		Holder.AslSoodAslMablagh.setText(NumberTextWatcherForThousand.getDecimalFormattedString((String) Holder.AslSoodAslMablagh.getText()));
		Holder.AslSoodSoodMablagh.setText(NumberTextWatcherForThousand.getDecimalFormattedString((String) Holder.AslSoodSoodMablagh.getText()));
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

}
