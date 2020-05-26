package ua.training.soap.producing.endpoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import tutorial.soapservice.*;
import ua.training.soap.producing.entity.CellPhone;
import ua.training.soap.producing.exception.CellPhoneAlreadyExistException;
import ua.training.soap.producing.exception.CellPhoneNotFoundException;
import ua.training.soap.producing.repository.CellPhoneRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CellPhoneEndpointTest {
    private static final Long ID = 0L;
    private static final String BRAND_NAME = "Brand";
    private static final String MODEL = "Model";
    private static final Double PRICE = 0.0;
    private static final OperatingSystem OPERATING_SYSTEM = OperatingSystem.ANDROID;

    @InjectMocks
    private CellPhoneEndpoint instance;

    @Mock
    private CellPhoneRepository repository;

    @Mock
    private CellPhone cellPhoneEntity;

    @Mock
    private tutorial.soapservice.CellPhone cellPhoneSoap;

    @Mock
    private GetCellPhoneRequest getCellPhoneRequest;

    @Mock
    private CreateCellPhoneRequest createCellPhoneRequest;

    @Before
    public void init() {
        when(cellPhoneEntity.getId()).thenReturn(ID);
        when(cellPhoneEntity.getBrandName()).thenReturn(BRAND_NAME);
        when(cellPhoneEntity.getModel()).thenReturn(MODEL);
        when(cellPhoneEntity.getPrice()).thenReturn(PRICE);
        when(cellPhoneEntity.getOperatingSystem())
                .thenReturn(ua.training.soap.producing.entity.OperatingSystem.ANDROID);
        when(cellPhoneSoap.getBrandName()).thenReturn(BRAND_NAME);
        when(cellPhoneSoap.getModel()).thenReturn(MODEL);
        when(cellPhoneSoap.getPrice()).thenReturn(PRICE);
        when(cellPhoneSoap.getOperatingSystem()).thenReturn(OPERATING_SYSTEM);
    }

    @Test
    public void getCellPhoneShouldReturnGetCellPhoneResponseWhenCellPhoneFound() {
        when(getCellPhoneRequest.getId()).thenReturn(ID);
        when(repository.findById(ID)).thenReturn(Optional.of(cellPhoneEntity));

        GetCellPhoneResponse result = instance.getCellPhone(getCellPhoneRequest);

        assertThat(result).isNotNull();
        assertThat(result.getCellphone().getId()).isEqualTo(ID);
        assertThat(result.getCellphone().getBrandName()).isEqualTo(BRAND_NAME);
        assertThat(result.getCellphone().getModel()).isEqualTo(MODEL);
        assertThat(result.getCellphone().getPrice()).isEqualTo(PRICE);
        assertThat(result.getCellphone().getOperatingSystem()).isEqualTo(OPERATING_SYSTEM);
    }

    @Test(expected = CellPhoneNotFoundException.class)
    public void getCellPhoneShouldReturnExceptionWhenCellPhoneNetExist() {
        when(getCellPhoneRequest.getId()).thenReturn(ID);
        when(repository.findById(ID)).thenReturn(Optional.empty());

        GetCellPhoneResponse result = instance.getCellPhone(getCellPhoneRequest);
    }

    @Test
    public void createCellPhoneShouldReturnCreateCellPhoneResponseWhenCellPhoneFound() {
        when(createCellPhoneRequest.getCellphone()).thenReturn(cellPhoneSoap);
        when(repository.save(any(CellPhone.class))).thenReturn(cellPhoneEntity);

        CreateCellPhoneResponse result = instance.createCellPhone(createCellPhoneRequest);

        assertThat(result).isNotNull();
        assertThat(result.getCellphone().getId()).isEqualTo(ID);
        assertThat(result.getCellphone().getBrandName()).isEqualTo(BRAND_NAME);
        assertThat(result.getCellphone().getModel()).isEqualTo(MODEL);
        assertThat(result.getCellphone().getPrice()).isEqualTo(PRICE);
        assertThat(result.getCellphone().getOperatingSystem()).isEqualTo(OPERATING_SYSTEM);
    }

    @Test(expected = CellPhoneAlreadyExistException.class)
    public void createCellPhoneShouldReturnExceptionWhenCellPhoneWasNotExist() {
        when(createCellPhoneRequest.getCellphone()).thenReturn(cellPhoneSoap);
        when(repository.save(any(CellPhone.class))).thenThrow(DataIntegrityViolationException.class);

        CreateCellPhoneResponse result = instance.createCellPhone(createCellPhoneRequest);
    }
}
