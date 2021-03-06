/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Api;

import java.io.File;
import java.io.FileNotFoundException;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author tifuivali
 */
public class RequestValidator {
    
    private String filePath=null;
    
    public RequestValidator(String filePath)
    {
        this.filePath=filePath;
    }
    
    public void validate() throws NotSuportedFileFormatException, FileNotFoundException
    {
        if(!checkFileExists())
            throw new FileNotFoundException();
        if(!checkFileFormat())
            throw new NotSuportedFileFormatException(FilenameUtils.getExtension(filePath));
    }
    
    private boolean checkFileExists()
    {
        File file=new File(filePath);
        return file.exists();
    }
    
    private boolean checkFileFormat()
    {
       String extension= FilenameUtils.getExtension(filePath);
       return extension.toLowerCase().equals("jpg")||extension.toLowerCase().equals("png");
    }
    
}
