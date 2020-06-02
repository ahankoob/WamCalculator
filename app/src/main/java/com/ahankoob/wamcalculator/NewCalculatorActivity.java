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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahankoob.wamcalculator.tools.MyBounceInterpolator;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewCalculatorActivity extends AppCompatActivity {
	private TextView ResultAghsatIcon,ResultAghsatTitle,ResultAghsatText
			,ResultSoodIcon,ResultSoodTitle,ResultSoodText
			,ResultbazpardakhtIcon,ResultbazpardakhtTitle,ResultbazpardakhtText,ActivityTitle;
	private ImageView bankInfoLogoImage;
	private TextInputEditText mablaghVam,darsadSood,bazpardakht;
	private ImageButton calcMohasebe,calcBazgasht,calcReset;
	private RelativeLayout FormLayout,ResultLayout;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_calculator);
		PreSetVars();
		setfonts();
		mablaghVam.addTextChangedListener(new NumberTextWatcherForThousand(mablaghVam));
		calcBazgasht.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						final Animation myAnim = AnimationUtils.loadAnimation(NewCalculatorActivity.this, R.anim.bounce);
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
				final Animation myAnim = AnimationUtils.loadAnimation(NewCalculatorActivity.this, R.anim.bounce);
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

					Double temp2 = 0.0;

					temp2 += Ghest * bazpardakhtAmount;
					temp2 -= sumVamAmount;

					Double temp3 = temp2 + sumVamAmount;
					ResultAghsatText.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(Integer.valueOf(Ghest.intValue()))) + " ریال");

					ResultSoodText.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(Integer.valueOf(temp2.intValue()))) + " ریال");


					ResultbazpardakhtText.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(Integer.valueOf(temp3.intValue()))) + " ریال");

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
						mablaghVam.setText("");
						darsadSood.setText("");
						bazpardakht.setText("");
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

		ResultAghsatIcon = (TextView)findViewById(R.id.ResultAghsatIcon);
		ResultAghsatTitle = (TextView)findViewById(R.id.ResultAghsatTitle);
		ResultAghsatText = (TextView)findViewById(R.id.ResultAghsatText);

		ResultSoodIcon = (TextView)findViewById(R.id.ResultSoodIcon);
		ResultSoodTitle = (TextView)findViewById(R.id.ResultSoofTitle);
		ResultSoodText = (TextView)findViewById(R.id.ResultSoodText);

		ResultbazpardakhtIcon = (TextView)findViewById(R.id.ResultbazpardakhtIcon);
		ResultbazpardakhtTitle = (TextView)findViewById(R.id.ResultbazpardakhtTitle);
		ResultbazpardakhtText = (TextView)findViewById(R.id.ResultbazpardakhtText);

		mablaghVam = (TextInputEditText) findViewById(R.id.mablaghVam);
		darsadSood = (TextInputEditText) findViewById(R.id.darsadSood);
		bazpardakht = (TextInputEditText) findViewById(R.id.bazpardakht);

		calcMohasebe = (ImageButton) findViewById(R.id.calcMohasebe);
		calcBazgasht = (ImageButton) findViewById(R.id.calcBazgasht);
		calcReset = (ImageButton) findViewById(R.id.calcReset);

		FormLayout = (RelativeLayout) findViewById(R.id.FormLayout);
		ResultLayout = (RelativeLayout) findViewById(R.id.ResultLayout);

	}
	public void setfonts(){
		FontManager.markAsIconContainer(ActivityTitle, FontManager.getVazirFont(getAssets()));

		FontManager.markAsIconContainer(ResultAghsatIcon, FontManager.getfontawesomeFont(getAssets()));
		FontManager.markAsIconContainer(ResultSoodIcon, FontManager.getfontawesomeFont(getAssets()));
		FontManager.markAsIconContainer(ResultbazpardakhtIcon, FontManager.getfontawesomeFont(getAssets()));

		FontManager.markAsIconContainer(ResultAghsatTitle, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(ResultSoodTitle, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(ResultbazpardakhtTitle, FontManager.getVazirFont(getAssets()));

		FontManager.markAsIconContainer(ResultAghsatText, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(ResultSoodText, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(ResultbazpardakhtText, FontManager.getVazirFont(getAssets()));

		FontManager.markAsIconContainer(mablaghVam, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(darsadSood, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(bazpardakht, FontManager.getVazirFont(getAssets()));

		FontManager.markAsIconContainer(calcMohasebe, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(calcBazgasht, FontManager.getVazirFont(getAssets()));
		FontManager.markAsIconContainer(calcReset, FontManager.getVazirFont(getAssets()));

	}
}

