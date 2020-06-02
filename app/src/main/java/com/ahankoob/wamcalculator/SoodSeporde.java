package com.ahankoob.wamcalculator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahankoob.wamcalculator.tools.MyBounceInterpolator;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

public class SoodSeporde extends AppCompatActivity {

	private TextView ResultSoodIcon,ResultSoodTitle,ResultSoodText;
	private TextInputEditText mablagh,darsadSood;
	private ImageButton calcMohasebe,calcBazgasht,calcReset;
	private RelativeLayout FormLayout,ResultLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sood_seporde);
		PreSetVars();
		setfonts();
		mablagh.addTextChangedListener(new NumberTextWatcherForThousand(mablagh));
		calcBazgasht.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						final Animation myAnim = AnimationUtils.loadAnimation(SoodSeporde.this, R.anim.bounce);
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
				final Animation myAnim = AnimationUtils.loadAnimation(SoodSeporde.this, R.anim.bounce);
				MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
				myAnim.setInterpolator(interpolator);
				calcMohasebe.startAnimation(myAnim);

				boolean isValid = true;
				if(mablagh.getText().toString().trim().length()==0)
				{
					mablagh.setError(getResources().getString(R.string.EmptyFieldError));
					isValid = false;
				}
				if(darsadSood.getText().toString().trim().length()==0)
				{
					darsadSood.setError(getResources().getString(R.string.EmptyFieldError));
					isValid = false;
				}

				if(isValid) {

					Double mablaghAmount = Double.valueOf(NumberTextWatcherForThousand.trimCommaOfString(mablagh.getText().toString().trim()));

					Double darsadSoodAmount = Double.valueOf(NumberTextWatcherForThousand.trimCommaOfString(darsadSood.getText().toString().trim()));
					Double MonthlySood = mablaghAmount*darsadSoodAmount/1200;

					ResultSoodText.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(Integer.valueOf(MonthlySood.intValue()))) + " ریال");

					/* Result Animation */
					final ObjectAnimator oa1 = ObjectAnimator.ofFloat(FormLayout, "scaleX", 1f, 0f);
					final ObjectAnimator oa2 = ObjectAnimator.ofFloat(ResultLayout, "scaleX", 0f, 1f);
					oa1.setInterpolator(new DecelerateInterpolator());
					oa2.setInterpolator(new AccelerateDecelerateInterpolator());
					oa1.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							super.onAnimationEnd(animation);
							calcMohasebe.setVisibility(View.GONE);
							calcReset.setVisibility(View.VISIBLE);
							FormLayout.setVisibility(View.GONE);
							ResultLayout.setVisibility(View.VISIBLE);

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

				final ObjectAnimator oa1 = ObjectAnimator.ofFloat(ResultLayout, "scaleX", 1f, 0f);
				final ObjectAnimator oa2 = ObjectAnimator.ofFloat(FormLayout, "scaleX", 0f, 1f);
				oa1.setInterpolator(new DecelerateInterpolator());
				oa2.setInterpolator(new AccelerateDecelerateInterpolator());
				oa1.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);
						mablagh.setText("");
						darsadSood.setText("");
						calcReset.setVisibility(View.GONE);
						calcMohasebe.setVisibility(View.VISIBLE);
						ResultLayout.setVisibility(View.GONE);
						FormLayout.setVisibility(View.VISIBLE);

						oa2.start();
					}
				});
				oa1.start();
			}
		});
	}
	public void PreSetVars(){

		ResultSoodIcon = (TextView)findViewById(R.id.ResultSoodIcon);
		ResultSoodTitle = (TextView)findViewById(R.id.ResultSoofTitle);
		ResultSoodText = (TextView)findViewById(R.id.ResultSoodText);

		mablagh = (TextInputEditText) findViewById(R.id.mablagh);
		darsadSood = (TextInputEditText) findViewById(R.id.darsadSood);

		calcMohasebe = (ImageButton) findViewById(R.id.calcMohasebe);
		calcBazgasht = (ImageButton) findViewById(R.id.calcBazgasht);
		calcReset = (ImageButton) findViewById(R.id.calcReset);

		FormLayout = (RelativeLayout) findViewById(R.id.FormLayout);
		ResultLayout = (RelativeLayout) findViewById(R.id.ResultLayout);

	}
	public void setfonts(){
		FontManager.markAsIconContainer(ResultSoodIcon, FontManager.getfontawesomeFont(getAssets()));
		FontManager.markAsIconContainer(ResultSoodTitle, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(ResultSoodText, FontManager.getVazirFont(getAssets()));

		FontManager.markAsIconContainer(mablagh, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(darsadSood, FontManager.getVazirFont(getAssets()));

		FontManager.markAsIconContainer(calcMohasebe, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(calcBazgasht, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(calcReset, FontManager.getVazirFont(getAssets()));

	}
}
