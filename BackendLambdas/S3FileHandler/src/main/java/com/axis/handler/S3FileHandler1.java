package com.axis.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.axis.config.DynamodbConfig;
import com.axis.dao.AgentDao;
import com.axis.dao.UserDao;
import com.axis.model.Agent;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Handler for requests to Lambda function.
 */
public class S3FileHandler1 implements RequestHandler<S3Event, String> {

    private AgentDao agentDao = new AgentDao(DynamodbConfig.dynamoDBMapper());
    Logger logger = Logger.getLogger(S3FileHandler1.class.getName());

    public String handleRequest(final S3Event s3event, final Context context) {
        List<S3EventNotification.S3EventNotificationRecord> records = s3event.getRecords();
        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
        for (S3EventNotification.S3EventNotificationRecord record :
                records) {
            if(record.getEventName().equals("ObjectCreated:Put")) {
                String srcBucket = record.getS3().getBucket().getName();
                String srcKey = record.getS3().getObject().getUrlDecodedKey();
                String [] arrOfName = record.getS3().getObject().getKey().split("\\.");
                if(arrOfName.length > 0 && (arrOfName[arrOfName.length-1].equals("xlsx") || arrOfName[arrOfName.length-1].equals("xls") )) {
                    // Download the image from S3 into a stream
                    S3Object s3Object = s3Client.getObject(new GetObjectRequest(
                            srcBucket, srcKey));
                    InputStream objectData = s3Object.getObjectContent();
                    try {
                        //Create Workbook instance holding reference to .xlsx file
                        XSSFWorkbook workbook = new XSSFWorkbook(objectData);
                        //Get first/desired sheet from the workbook
                        XSSFSheet sheet = workbook.getSheetAt(0);
                        //Iterate through each rows one by one
                        Iterator<Row> rowIterator = sheet.iterator();
                        rowIterator.next();//skipping first row
                        while (rowIterator.hasNext())
                        {
                            int index = 0;
                            Row row = rowIterator.next();
                            //For each row, iterate through all the columns
                            Iterator<Cell> cellIterator = row.cellIterator();
                            DataFormatter formatter = new DataFormatter();
                            Agent agent = null;
                            agent = new Agent();
                            while (cellIterator.hasNext())
                            {
                                index++;
                                Cell cell = cellIterator.next();
                                switch (index) {
                                    case 1:
                                        Object value = formatter.formatCellValue(cell);
                                        agent.setEmail((String) value);
                                        break;
                                    case 2:
                                        Object value1 = formatter.formatCellValue(cell);
                                        agent.setName((String) value1);
                                        break;
                                    case 3:
                                        Object value2 = formatter.formatCellValue(cell);
                                        agent.setAddress((String) value2);
                                        break;
                                    case 4:
                                        agent.setDob(cell.getDateCellValue());
                                        break;
                                    case 5:
                                        Object value3 = formatter.formatCellValue(cell);
                                        agent.setCrmId((String) value3);
                                        break;
                                    case 6:
                                        Object value4 = formatter.formatCellValue(cell);
                                        agent.setTeleCallingId((String) value4);
                                        break;
                                    case 7:
                                        Object value5 = formatter.formatCellValue(cell);
                                        agent.setLicenseUrnNo((String) value5);
                                        break;
                                    case 8:
                                        agent.setLicenseIssueDate(cell.getDateCellValue());
                                        break;
                                    case 9:
                                        agent.setLicenseExpiryDate(cell.getDateCellValue());
                                        break;
                                    default:break;
                                }
                            }
                            if(agent.getUserId() != null ) {
                                agentDao.updateAgentDetails(agent);
                            }
                        }
                    } catch (IOException e) {
                        logger.severe(e.getMessage());
                    }
                }
            }
        }
        return "";
    }

}
