/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Api;

import ImageModel.ImageProcessedRepresentation;
import ImagePreProcesing.TruncatingException;
import ImagePreProcesing.ValidatingException;
import ImageProcesing.ProcesingException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author tifuivali
 */
public class PublicApiService
{

    RequestValidator requestValidator=null;
    Api api=null;
    
    public PublicApiService()
    {
        
    }
    
    /**
     *Process the request , if type request is internal ,first parameter can be null.
     * @param fileString
     * @param requestType
     * @throws Api.InproperParameterCallingRequestException
     * @throws Api.NotSuportedFileFormatException
     * @throws java.io.FileNotFoundException
     */
    public void processRequest(String fileString,RequestType requestType) throws InproperParameterCallingRequestException, NotSuportedFileFormatException, FileNotFoundException
    {
        if(fileString==null&&requestType==RequestType.ExternalRequestType)
            throw new InproperParameterCallingRequestException();
        requestValidator=new RequestValidator(fileString);
        requestValidator.validate();
        api=ChooseRightImageApi(fileString, requestType);
       
    }
    
    public ImageProcessedRepresentation getResult() throws ValidatingException, TruncatingException, ProcesingException
    {
        return api.getImageProcessedRepresentation();
    }
    
    private Api ChooseRightImageApi(String fileName,RequestType type) throws FileNotFoundException
    {
        if(type==RequestType.ExternalRequestType)
            return new ExternalApiImpl(new File(fileName));
        else
        {
            return new InternalApiImpl();
        }
    }

}
