package net.idoun.photocategorizer;

import org.apache.sanselan.ImageParser;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ImageExifExtractor {
    public static final String EXIF_DATE_FORMAT = "yyyy:MM:dd HH:mm:ss";
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd";

    private final File file;
    private final ImageParser parser;

    public ImageExifExtractor(File file, ImageParser parser) {
        this.file = file;
        this.parser = parser;
    }

    public String getCreateDate(final String format) {
        Calendar cal = Calendar.getInstance();

        try {
            final IImageMetadata metadata = parser.getMetadata(file);

            JpegImageMetadata jpegMetadata = (JpegImageMetadata)metadata;

            if (metadata == null) {
                System.out.println("\tFile is not JPEG.");
                return null;
            }

            TiffField field = jpegMetadata.findEXIFValue(ExifTagConstants.EXIF_TAG_CREATE_DATE);
            String createDate = field.getValueDescription().replaceAll("'", "");
            SimpleDateFormat formatForParsing = new SimpleDateFormat(EXIF_DATE_FORMAT, Locale.getDefault());

            cal.setTime(formatForParsing.parse(createDate));
        } catch (IOException ioe) {
            System.out.println("IOException");
            ioe.printStackTrace();
        } catch (ImageReadException ire) {
            System.out.println("ImageReadException");
            ire.printStackTrace();
        } catch (ParseException e) {
            System.out.println("ParseException");
            e.printStackTrace();
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(cal.getTime());
    }
}
