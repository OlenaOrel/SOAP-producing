package ua.training.soap.producing.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import tutorial.soapservice.*;
import ua.training.soap.producing.entity.CellPhone;
import ua.training.soap.producing.exception.CellPhoneAlreadyExistException;
import ua.training.soap.producing.exception.CellPhoneNotFoundException;
import ua.training.soap.producing.repository.CellPhoneRepository;

@Endpoint
public class CellPhoneEndpoint {
    private static final String NAMESPACE_URI = "http://soapService.tutorial";

    private CellPhoneRepository repository;

    private ObjectFactory objectFactory;

    @Autowired
    public CellPhoneEndpoint(CellPhoneRepository repository) {
        this.repository = repository;
        objectFactory = new ObjectFactory();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCellPhoneRequest")
    @ResponsePayload
    public GetCellPhoneResponse getCellPhone(@RequestPayload GetCellPhoneRequest request) {
        GetCellPhoneResponse response = objectFactory.createGetCellPhoneResponse();

        CellPhone cellPhoneById = repository.findById(request.getId())
                .orElseThrow(() ->
                        new CellPhoneNotFoundException("Cell phone with id = "
                                + request.getId() + "not found"
                        ));
        response.setCellphone(convertCellPhoneFromEntityToSoapObject(cellPhoneById));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createCellPhoneRequest")
    @ResponsePayload
    public CreateCellPhoneResponse createCellPhone(@RequestPayload CreateCellPhoneRequest request) {
        CreateCellPhoneResponse response = objectFactory.createCreateCellPhoneResponse();
        CellPhone cellPhone = getCellPhoneEntityFromRequest(request.getCellphone());

        try {
            CellPhone cellPhoneSaved = repository.save(cellPhone);
            response.setCellphone(convertCellPhoneFromEntityToSoapObject(cellPhoneSaved));
        } catch (DataIntegrityViolationException e) {
            throw new CellPhoneAlreadyExistException("Cell phone is exist");
        }
        return response;
    }

    private CellPhone getCellPhoneEntityFromRequest(tutorial.soapservice.CellPhone cellPhoneFromRequest) {
        CellPhone cellPhone = new CellPhone();
        cellPhone.setBrandName(cellPhoneFromRequest.getBrandName());
        cellPhone.setModel(cellPhoneFromRequest.getModel());
        cellPhone.setPrice(cellPhoneFromRequest.getPrice());
        cellPhone.setOperatingSystem(ua.training.soap.producing.entity.OperatingSystem
                .valueOf(cellPhoneFromRequest.getOperatingSystem().name()));
        return cellPhone;
    }

    private tutorial.soapservice.CellPhone convertCellPhoneFromEntityToSoapObject(CellPhone cellPhoneFromDb) {
        tutorial.soapservice.CellPhone cellPhone = objectFactory.createCellPhone();
        cellPhone.setId(cellPhoneFromDb.getId());
        cellPhone.setBrandName(cellPhoneFromDb.getBrandName());
        cellPhone.setModel(cellPhoneFromDb.getModel());
        cellPhone.setPrice(cellPhoneFromDb.getPrice());
        cellPhone.setOperatingSystem(OperatingSystem
                .valueOf(cellPhoneFromDb.getOperatingSystem().name()));
        return cellPhone;
    }

}
