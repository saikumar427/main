package com.seatingarrangement.dbhelpers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.StringTokenizer;

import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.StatusObj;

public class SeatingFileDataHelper {

	public static void readAndInsertSeatsData(String filePath, String venue_id,
			String section_id) {
		ArrayList<String> fileData = new ArrayList<String>();
		StatusObj statobj = null;
		try {

			String delete_venueseats = "delete from venue_seats where venue_id=to_number(?,'999999999') and section_id=to_number(?,'999999999')";
			statobj = DbUtil.executeUpdateQuery(delete_venueseats,
					new String[] { venue_id, section_id });
			//System.out.println("status of delete venueseats"+statobj.getStatus
			// ());

			BufferedReader br = null;
			String filepath = EbeeConstantsF.get("temp.upload.filepath",
					"C:\\uploads");
			String fileName = "section_" + venue_id + "_" + section_id + ".csv";
			InputStream is = new FileInputStream(filepath + "/" + fileName);
			br = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
			String s = null;
			int row = 1;
			while ((s = br.readLine()) != null) {

				String row_id = "" + row++;
				s = s.replaceAll("\"", "");
				s = s.replaceAll(",", " , ");

				StringTokenizer st = new StringTokenizer(s, ",");
				int tkns = st.countTokens();
				int col = 1;

				if (tkns < 1) {
					System.out.println("There is no data");
				} else {

					while (st.hasMoreTokens()) {

						String seatcode = (String) st.nextToken().trim();
						String isseat = null;
						String seatindex = "";

						if ("".equals(seatcode) || seatcode == null
								|| "EMPTY".equals(seatcode)) {
							isseat = "N";
							// seatcode="";
						} else {
							isseat = "Y";

						}
						String col_id = "" + col++;

						seatindex = section_id + "_" + row_id + "_" + col_id;

						String venueseats = "insert into venue_seats(venue_id,section_id,row_id,col_id,seatindex,isseat,seatcode)"
								+ "values(to_number(?,'999999999'),to_number(?,'999999999'),to_number(?,'999999999'),to_number(?,'999999999'),?,?,?)";
						statobj = DbUtil.executeUpdateQuery(venueseats,
								new String[] { venue_id, section_id, row_id,
										col_id, seatindex, isseat, seatcode });
						// System.out.println("status of venueseats"+statobj.
						// getStatus());

					}// end of second while

				}// end of else

			}

			br.close();
		} // End try block
		catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}
		// return fileData;

	}

	public static ArrayList<String> populateSeatFields(String venue_id,
			String section_id) {

		ArrayList<String> Fields = new ArrayList<String>();
		DBManager db = new DBManager();
		StatusObj statobj = null;

		String venue_seats = "select distinct col_id from venue_seats where venue_id=to_number(?,'999999999') and section_id=to_number(?,'999999999')";
		statobj = db.executeSelectQuery(venue_seats, new String[] { venue_id,
				section_id });
		System.out.println("status of seat fields " + statobj.getStatus());

		if (statobj.getStatus()) {
			for (int i = 0; i < statobj.getCount(); i++) {

				String col_id = db.getValue(i, "col_id", "");
				// System.out.println("col_id" + col_id);
				Fields.add(col_id);

			}

		}

		return Fields;
	}

	public static ArrayList<HashMap<String, String>> getVenueSeats(
			String venue_id, String section_id, String venuename) {
		
		// System.out.println("venueid: " + venue_id + ", section_id: "+ section_id + ", venuename :" + venuename);
		ArrayList<HashMap<String, String>> seatsList = new ArrayList<HashMap<String, String>>();

		System.out.println("in getVenueSeats");
		DBManager db = new DBManager();
		DBManager dbm = new DBManager();
		StatusObj statobj = null;

		String rows = "select distinct row_id from venue_seats where venue_id=to_number(?,'999999999') and section_id=to_number(?,'999999999')";
		StatusObj status = dbm.executeSelectQuery(rows, new String[] {venue_id, section_id });
		// System.out.println("status in  get venue seats of row string "+ status.getStatus());
		
		if (status.getStatus()) {
			for (int i = 0; i < status.getCount(); i++) {
				String row_id = dbm.getValue(i, "row_id", "");
			
				String venue_seats = "select seatcode,col_id from venue_seats where venue_id=to_number(?,'999999999') and section_id=to_number(?,'999999999') and row_id=to_number(?,'999999999')";
				statobj = db.executeSelectQuery(venue_seats, new String[] {venue_id, section_id, row_id });
				// System.out.println("status in  get venue seats "+ statobj.getStatus());
				if (statobj.getStatus()) {
					HashMap<String, String> seatsinfo = new HashMap<String, String>();
					for (int j = 0; j < statobj.getCount(); j++) {

						String col_id = db.getValue(j, "col_id", "");
						String seatcode = db.getValue(j, "seatcode", "");

						// System.out.println("col_id"+col_id+","+seatcode+","+	 row_id);
						seatsinfo.put(col_id, seatcode);

					}
					seatsList.add(seatsinfo);
				}

			}
		}

		System.out.println("seat size" + seatsList.size());

		return seatsList;
	}

}
