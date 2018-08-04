package com.tmac.aspose;

import com.aspose.cells.Range;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExcelPreviewService {
    public Map<String, String> preview(final MultipartFile file) throws Exception {

        final Workbook sourceWorkBook = new Workbook(file.getInputStream());
        final WorksheetCollection sourceWorkBookWorksheets = sourceWorkBook.getWorksheets();
        final Map<String, String> result = new HashMap<>();

        for (Integer i = 0; i < sourceWorkBookWorksheets.getCount(); i++) {
            final Worksheet sourceWorksheet = sourceWorkBookWorksheets.get(i);
            final Range sourceRange = sourceWorksheet.getCells().getMaxDisplayRange();

            final Workbook destWorkbook = new Workbook();
            final Worksheet destWorksheet = destWorkbook.getWorksheets().get(0);
            destWorksheet.setName(sourceWorksheet.getName());
            final Range destRange = destWorksheet.getCells().createRange(sourceRange.getFirstRow() + sourceRange.getRowCount()
                    , sourceRange.getFirstColumn(), sourceRange.getRowCount(), sourceRange.getColumnCount());
            destRange.copy(sourceRange);

            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            com.aspose.cells.HtmlSaveOptions opts = new com.aspose.cells.HtmlSaveOptions(com.aspose.cells.SaveFormat.HTML);
            opts.setExportImagesAsBase64(true);
            opts.setExportActiveWorksheetOnly(true);

            destWorkbook.save(byteArrayOutputStream, opts);

            final String resultHtml = new String(byteArrayOutputStream.toByteArray());
            result.put(sourceWorksheet.getName(), resultHtml);
        }
        return result;
    }
}
