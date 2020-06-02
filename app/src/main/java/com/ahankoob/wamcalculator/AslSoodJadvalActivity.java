package com.ahankoob.wamcalculator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.ahankoob.wamcalculator.adapters.asl_sood_recycler_adapter;
import com.ahankoob.wamcalculator.models.ghest;
import com.ahankoob.wamcalculator.tools.MyBounceInterpolator;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AslSoodJadvalActivity extends AppCompatActivity {
	RecyclerView AslSoodRecyclerView;
	ArrayList<ghest> items;
	private TextInputEditText mablaghVam,darsadSood,bazpardakht;
	private ImageButton calcMohasebe,calcBazgasht,calcReset;
	private RelativeLayout FormLayout,ListLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asl_sood_jadval);
		PreSetVars();
		setfonts();
		mablaghVam.addTextChangedListener(new NumberTextWatcherForThousand(mablaghVam));
		calcBazgasht.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						final Animation myAnim = AnimationUtils.loadAnimation(AslSoodJadvalActivity.this, R.anim.bounce);
						MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
						myAnim.setInterpolator(interpolator);
						calcBazgasht.startAnimation(myAnim);
						//startActivity(new Intent(CalculatorActivity.this,MainActivity.class));
						finish();
					}
				}, 350);


			}
		});
		calcMohasebe.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Animation myAnim = AnimationUtils.loadAnimation(AslSoodJadvalActivity.this, R.anim.bounce);
				MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
				myAnim.setInterpolator(interpolator);
				calcMohasebe.startAnimation(myAnim);

				boolean isValid = true;
				if(mablaghVam.getText().toString().trim().length()==0)
				{
					mablaghVam.setError(getResources().getString(R.string.EmptyFieldError));
					isValid = false;
				}
				if(darsadSood.getText().toString().trim().length()==0)
				{
					darsadSood.setError(getResources().getString(R.string.EmptyFieldError));
					isValid = false;
				}
				if(bazpardakht.getText().toString().trim().length()==0)
				{
					bazpardakht.setError(getResources().getString(R.string.EmptyFieldError));
					isValid = false;
				}
				if(isValid) {
					items = new ArrayList<ghest>();

					Double sumVamAmount = Double.valueOf(NumberTextWatcherForThousand.trimCommaOfString(mablaghVam.getText().toString().trim()));

					Double darsadSoodAmount = Double.valueOf(NumberTextWatcherForThousand.trimCommaOfString(darsadSood.getText().toString().trim()));

					Double bazpardakhtAmount = Double.valueOf(NumberTextWatcherForThousand.trimCommaOfString(bazpardakht.getText().toString().trim()));

					Double soorat = 0.0;
					Double temp1 = darsadSoodAmount / 1200;
					soorat += Math.pow((temp1 + 1), bazpardakhtAmount);

					soorat *= temp1;
					soorat *= sumVamAmount;

					Double makhraj = 0.0;

					makhraj += Math.pow((temp1 + 1), bazpardakhtAmount);
					makhraj -= 1;
					Double Ghest = (soorat / makhraj);


					for(int m=1;m<=bazpardakhtAmount;m++){
						Double Soorat=0.0;
						Double _r = darsadSoodAmount/1200;
						Double Soorat_1 = Math.pow((1+_r),(bazpardakhtAmount+1));
						Double Soorat_2 = Math.pow((1+_r),m);
						Soorat = Soorat_1-Soorat_2;

						Double Makhraj = 0.0;
						Double Makhraj_1 = Math.pow((1+_r),(bazpardakhtAmount+1));
						Double Makhraj_2 = 1+_r;

						Makhraj = Makhraj_1-Makhraj_2;

						Double im = sumVamAmount*_r*(Soorat/Makhraj); //Sood Ghest m
						Double pm = Ghest-im;

						items.add(new ghest(m,Ghest,pm,im));


					}

					asl_sood_recycler_adapter adapter = new asl_sood_recycler_adapter(AslSoodJadvalActivity.this,items);
					RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(AslSoodJadvalActivity.this, 1);
					AslSoodRecyclerView.setLayoutManager(mLayoutManager);
					AslSoodRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
					AslSoodRecyclerView.setItemAnimator(new DefaultItemAnimator());
					AslSoodRecyclerView.setAdapter(adapter);



					/* Result Animation */
					final ObjectAnimator oa1 = ObjectAnimator.ofFloat(FormLayout, "scaleX", 1f, 0f);
					final ObjectAnimator oa2 = ObjectAnimator.ofFloat(ListLayout, "scaleX", 0f, 1f);
					oa1.setInterpolator(new DecelerateInterpolator());
					oa2.setInterpolator(new AccelerateDecelerateInterpolator());
					oa1.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							super.onAnimationEnd(animation);
							calcMohasebe.setVisibility(View.GONE);
							calcReset.setVisibility(View.VISIBLE);
							FormLayout.setVisibility(View.GONE);
							ListLayout.setVisibility(View.VISIBLE);

							oa2.start();
						}
					});
					oa1.start();

				}



			}
		});
		calcReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final ObjectAnimator oa1 = ObjectAnimator.ofFloat(ListLayout, "scaleX", 1f, 0f);
				final ObjectAnimator oa2 = ObjectAnimator.ofFloat(FormLayout, "scaleX", 0f, 1f);
				oa1.setInterpolator(new DecelerateInterpolator());
				oa2.setInterpolator(new AccelerateDecelerateInterpolator());
				oa1.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);
						mablaghVam.setText("");
						darsadSood.setText("");
						bazpardakht.setText("");
						calcReset.setVisibility(View.GONE);
						calcMohasebe.setVisibility(View.VISIBLE);
						ListLayout.setVisibility(View.GONE);
						FormLayout.setVisibility(View.VISIBLE);

						oa2.start();
					}
				});
				oa1.start();
			}
		});
	}
	public void PreSetVars(){
		AslSoodRecyclerView = (RecyclerView) findViewById(R.id.AslSoodRecyclerView);

		mablaghVam = (TextInputEditText) findViewById(R.id.mablaghVam);
		darsadSood = (TextInputEditText) findViewById(R.id.darsadSood);
		bazpardakht = (TextInputEditText) findViewById(R.id.bazpardakht);

		calcMohasebe = (ImageButton) findViewById(R.id.calcMohasebe);
		calcBazgasht = (ImageButton) findViewById(R.id.calcBazgasht);
		calcReset = (ImageButton) findViewById(R.id.calcReset);

		FormLayout = (RelativeLayout) findViewById(R.id.FormLayout);
		ListLayout = (RelativeLayout) findViewById(R.id.ListLayout);

	}
	public void setfonts(){
		FontManager.markAsIconContainer(AslSoodRecyclerView, FontManager.getVazirFont(getAssets()));

		FontManager.markAsIconContainer(mablaghVam, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(darsadSood, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(bazpardakht, FontManager.getVazirFont(getAssets()));

		FontManager.markAsIconContainer(calcMohasebe, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(calcBazgasht, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(calcReset, FontManager.getVazirFont(getAssets()));

	}
	public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

		private int spanCount;
		private int spacing;
		private boolean includeEdge;

		public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
			this.spanCount = spanCount;
			this.spacing = spacing;
			this.includeEdge = includeEdge;
		}

		@Override
		public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
			int position = parent.getChildAdapterPosition(view); // item position
			int column = position % spanCount; // item column

			if (includeEdge) {
				outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
				outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

				if (position < spanCount) { // top edge
					outRect.top = spacing;
				}
				outRect.bottom = spacing; // item bottom
			} else {
				outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
				outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
				if (position >= spanCount) {
					outRect.top = spacing; // item top
				}
			}
		}
	}

	/**
	 * Converting dp to pixel
	 */
	private int dpToPx(int dp) {
		Resources r = getResources();
		return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
	}

}

