import java.util.ArrayList;
import java.util.List;


public class Scrubber {
	public static String scrub(String email) {
		List<Integer> points = new ArrayList<>();
		int prevIndex = 0;
		int idx;
		while ((idx = email.indexOf("<img ", prevIndex + 1)) != -1) {
			if (prevIndex == idx) break;
			prevIndex = idx;
			int i = idx;
			String imgTag = "";
			while (email.charAt(idx) != '>') {
				System.out.print(email.charAt(idx));
				char a = email.charAt(idx);
				if (!(a == '\r'))
					imgTag += email.charAt(idx);
				else
					imgTag = imgTag.substring(0, imgTag.length() - 1);
				idx++;
			}
			imgTag += email.charAt(idx);
			idx++;
			
			// strip lines
			imgTag = imgTag.replace("\n", "").replace("\r", "");
			
			// check if the img is too small to see
			if (imgTag.matches(".*height=(3D)?['\"]?[01]['\"]?[ >].*") && imgTag.matches(".*width=(3D)?['\"]?[01]['\"]?[ >].*")) {
				points.add(prevIndex);
				points.add(idx);
			}
		}
		
		if (!points.isEmpty()) {
			StringBuilder cleaned = new StringBuilder();
			int j, curr = 0, offset = 0;
			
			for (; curr < points.size(); curr += 2) {
				int p1 = points.get(curr);
				int p2 = points.get(curr + 1);
				
				cleaned.append(email.substring(offset, p1));
				for (j = 0; j < (p2 - p1); j++) {
					if (email.charAt(p1+j) == '\n')
						cleaned.append("\n");
					else if (email.charAt(p1+j) == '\r')
						cleaned.append("\r");
					else
						cleaned.append("*");
				}
				offset = p2;
			}
			
			cleaned.append(email.substring(offset, email.length()));
			String clean = cleaned.toString();
			return clean;
		}
		
		return email;
	}
}
