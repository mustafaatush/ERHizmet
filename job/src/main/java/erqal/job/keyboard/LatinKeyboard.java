/*
 * Copyright (C) 2008-2009 Google Inc.
 * Copyright (C) 2009 Alejandro Grijalba
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package erqal.job.keyboard;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;
import android.view.inputmethod.EditorInfo;

import erqal.job.R;


/*
 * This class holds the data structures for the keyboard
 * Does not perform any funny task 
 */
public class LatinKeyboard extends Keyboard {
	private Key mEnterKey;
	Context ctx;

	public LatinKeyboard(Context context, int xmlLayoutResId) {
		super(context, xmlLayoutResId);
	}

	public LatinKeyboard(Context context, int layoutTemplateResId,
			CharSequence characters, int columns, int horizontalPadding) {
		super(context, layoutTemplateResId, characters, columns,
				horizontalPadding);
		this.ctx = context;
	}

	@Override
	protected Key createKeyFromXml(Resources res, Row parent, int x, int y,
			XmlResourceParser parser) {

		LatinKey key = new LatinKey(res, parent, x, y, parser);

		if (key.icon == null) {
			key.icon = new fancyLabelDraw(key);
			key.iconPreview = new TextDrawable(key, ctx);
		}

		if (key.codes[0] == 10) {
			mEnterKey = key;
		}

		return key;
	}

	/**
	 * This looks at the ime options given by the current editor, to set the
	 * appropriate label on the keyboard's enter key (if it has one).
	 */
	void setImeOptions(Resources res, int options) {
		if (mEnterKey == null) {
			return;
		}

		switch (options
				& (EditorInfo.IME_MASK_ACTION | EditorInfo.IME_FLAG_NO_ENTER_ACTION)) {
		case EditorInfo.IME_ACTION_GO:
			mEnterKey.iconPreview = null;
			mEnterKey.icon = null;
			// mEnterKey.label = res.getText(R.string.label_go_key);
			break;
		case EditorInfo.IME_ACTION_NEXT:
			mEnterKey.iconPreview = null;
			mEnterKey.icon = null;
			// mEnterKey.label = res.getText(R.string.label_next_key);
			break;
		case EditorInfo.IME_ACTION_SEARCH:
			mEnterKey.icon = res
					.getDrawable(R.drawable.keyboard_sym_keyboard_return);
			mEnterKey.label = null;
			break;
		case EditorInfo.IME_ACTION_SEND:
			mEnterKey.iconPreview = null;
			mEnterKey.icon = null;
			// mEnterKey.label = res.getText(R.string.label_send_key);
			break;
		default:
			mEnterKey.icon = res
					.getDrawable(R.drawable.keyboard_sym_keyboard_return);
			mEnterKey.label = null;
			break;
		}
	}

	public static class LatinKey extends Key {

		public CharSequence fancyLabel;

		public LatinKey(Resources res, Row parent, int x, int y,
				XmlResourceParser parser) {
			super(res, parent, x, y, parser);
			this.fancyLabel = label;
			if (fancyLabel != null)
				fancyLabel = fancyLabel + "";
			this.label = null;

		}

	}

}
