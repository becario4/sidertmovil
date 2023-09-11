package com.sidert.sidertmovil.v2.di.modules;

import com.sidert.sidertmovil.v2.SidertMovilApplication;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.v2.domain.AppDatabase;
import com.sidert.sidertmovil.v2.domain.daos.*;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public interface DatabaseModule {

    @Singleton
    @Provides
    static AppDatabase provideAppDatabase(SidertMovilApplication application) {
        return Room.databaseBuilder(
                        application.getApplicationContext(),
                        AppDatabase.class,
                        DBhelper.DATABASE_NAME
                )
                .createFromAsset("database/sidertmovil.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    static AmortizacionesDao provideAmortizacionesDao(AppDatabase appDatabase) {
        return appDatabase.amortizacionesDao();
    }

    @Singleton
    @Provides
    static ArqueoCajaDao provideArqueoCajaDao(AppDatabase appDatabase) {
        return appDatabase.arqueoCajaDao();
    }

    @Singleton
    @Provides
    static AvalDao provideAvalDao(AppDatabase appDatabase) {
        return appDatabase.avalDao();
    }

    @Singleton
    @Provides
    static AvalAutoDao provideAvalAutoDao(AppDatabase appDatabase) {
        return appDatabase.avalAutoDao();
    }

    @Singleton
    @Provides
    static AvalIndDao provideAvalIndDao(AppDatabase appDatabase) {
        return appDatabase.avalIndDao();
    }

    @Singleton
    @Provides
    static AvalRenDao provideAvalRenDao(AppDatabase appDatabase) {
        return appDatabase.avalRenDao();
    }

    @Singleton
    @Provides
    static CancelacionesDao provideCancelacionesDao(AppDatabase appDatabase) {
        return appDatabase.cancelacionesDao();
    }

    @Singleton
    @Provides
    static CarteraGrupoDao provideCarteraGrupoDao(AppDatabase appDatabase) {
        return appDatabase.carteraGrupoDao();
    }

    @Singleton
    @Provides
    static CarteraIndividualDao provideCarteraIndividualDao(AppDatabase appDatabase) {
        return appDatabase.carteraIndividualDao();
    }

    @Singleton
    @Provides
    static CatColoniasDao provideCatColoniasDao(AppDatabase appDatabase) {
        return appDatabase.catColoniasDao();
    }

    @Singleton
    @Provides
    static CatalogoCampanasDao provideCatalogoCampanasDao(AppDatabase appDatabase) {
        return appDatabase.catalogoCampanasDao();
    }

    @Singleton
    @Provides
    static CentroCostoDao provideCentroCostoDao(AppDatabase appDatabase) {
        return appDatabase.centroCostoDao();
    }

    @Singleton
    @Provides
    static CierreDiaDao provideCierreDiaDao(AppDatabase appDatabase) {
        return appDatabase.cierreDiaDao();
    }

    @Singleton
    @Provides
    static ClienteIndDao provideClienteIndDao(AppDatabase appDatabase) {
        return appDatabase.clienteIndDao();
    }

    @Singleton
    @Provides
    static ClienteIndAutoDao provideClienteIndAutoDao(AppDatabase appDatabase) {
        return appDatabase.clienteIndAutoDao();
    }

    @Singleton
    @Provides
    static ClienteIndRenDao provideClienteIndRenDao(AppDatabase appDatabase) {
        return appDatabase.clienteIndRenDao();
    }

    @Singleton
    @Provides
    static CodigosOxxoDao provideCodigosOxxoDao(AppDatabase appDatabase) {
        return appDatabase.codigosOxxoDao();
    }

    @Singleton
    @Provides
    static ConsultaCcDao provideConsultaCcDao(AppDatabase appDatabase) {
        return appDatabase.consultaCcDao();
    }

    @Singleton
    @Provides
    static ConyugeIndDao provideConyugeIndDao(AppDatabase appDatabase) {
        return appDatabase.conyugeIndDao();
    }

    @Singleton
    @Provides
    static ConyugeIndAutoDao provideConyugeIndAutoDao(AppDatabase appDatabase) {
        return appDatabase.conyugeIndAutoDao();
    }

    @Singleton
    @Provides
    static ConyugeIndRenDao provideConyugeIndRenDao(AppDatabase appDatabase) {
        return appDatabase.conyugeIndRenDao();
    }

    @Singleton
    @Provides
    static ConyugeIntegranteDao provideConyugeIntegranteDao(AppDatabase appDatabase) {
        return appDatabase.conyugeIntegranteDao();
    }

    @Singleton
    @Provides
    static ConyugeIntegranteAutoDao provideConyugeIntegranteAutoDao(AppDatabase appDatabase) {
        return appDatabase.conyugeIntegranteAutoDao();
    }

    @Singleton
    @Provides
    static ConyugeIntegranteRenDao provideConyugeIntegranteRenDao(AppDatabase appDatabase) {
        return appDatabase.conyugeIntegranteRenDao();
    }

    @Singleton
    @Provides
    static CreditoGpoDao provideCreditoGpoDao(AppDatabase appDatabase) {
        return appDatabase.creditoGpoDao();
    }

    @Singleton
    @Provides
    static CreditoGpoAutoDao provideCreditoGpoAutoDao(AppDatabase appDatabase) {
        return appDatabase.creditoGpoAutoDao();
    }

    @Singleton
    @Provides
    static CreditoGpoRenDao provideCreditoGpoRenDao(AppDatabase appDatabase) {
        return appDatabase.creditoGpoRenDao();
    }

    @Singleton
    @Provides
    static CreditoIndDao provideCreditoIndDao(AppDatabase appDatabase) {
        return appDatabase.creditoIndDao();
    }

    @Singleton
    @Provides
    static CreditoIndAutoDao provideCreditoIndAutoDao(AppDatabase appDatabase) {
        return appDatabase.creditoIndAutoDao();
    }

    @Singleton
    @Provides
    static CreditoIndRenDao provideCreditoIndRenDao(AppDatabase appDatabase) {
        return appDatabase.creditoIndRenDao();
    }

    @Singleton
    @Provides
    static CroquisGpoDao provideCroquisGpoDao(AppDatabase appDatabase) {
        return appDatabase.croquisGpoDao();
    }

    @Singleton
    @Provides
    static CroquisGpoAutoDao provideCroquisGpoAutoDao(AppDatabase appDatabase) {
        return appDatabase.croquisGpoAutoDao();
    }

    @Singleton
    @Provides
    static CroquisGpoRenDao provideCroquisGpoRenDao(AppDatabase appDatabase) {
        return appDatabase.croquisGpoRenDao();
    }

    @Singleton
    @Provides
    static CroquisIndDao provideCroquisIndDao(AppDatabase appDatabase) {
        return appDatabase.croquisIndDao();
    }

    @Singleton
    @Provides
    static CroquisIndAutoDao provideCroquisIndAutoDao(AppDatabase appDatabase) {
        return appDatabase.croquisIndAutoDao();
    }

    @Singleton
    @Provides
    static CroquisIndRenDao provideCroquisIndRenDao(AppDatabase appDatabase) {
        return appDatabase.croquisIndRenDao();
    }

    @Singleton
    @Provides
    static DatosBeneficiarioGpoDao provideDatosBeneficiarioGpoDao(AppDatabase appDatabase) {
        return appDatabase.datosBeneficiarioGpoDao();
    }

    @Singleton
    @Provides
    static DatosBeneficiarioGpoRenDao provideDatosBeneficiarioGpoRenDao(AppDatabase appDatabase) {
        return appDatabase.datosBeneficiarioGpoRenDao();
    }

    @Singleton
    @Provides
    static DatosBeneficiarioIndDao provideDatosBeneficiarioIndDao(AppDatabase appDatabase) {
        return appDatabase.datosBeneficiarioIndDao();
    }

    @Singleton
    @Provides
    static DatosBeneficiarioIndRenDao provideDatosBeneficiarioIndRenDao(AppDatabase appDatabase) {
        return appDatabase.datosBeneficiarioIndRenDao();
    }

    @Singleton
    @Provides
    static DatosCreditoCampanaDao provideDatosCreditoCampanaDao(AppDatabase appDatabase) {
        return appDatabase.datosCreditoCampanaDao();
    }

    @Singleton
    @Provides
    static DatosCreditoCampanaGpoDao provideDatosCreditoCampanaGpoDao(AppDatabase appDatabase) {
        return appDatabase.datosCreditoCampanaGpoDao();
    }

    @Singleton
    @Provides
    static DatosCreditoCampanaGpoRenDao provideDatosCreditoCampanaGpoRenDao(AppDatabase appDatabase) {
        return appDatabase.datosCreditoCampanaGpoRenDao();
    }

    @Singleton
    @Provides
    static DatosEntregaCarteraDao provideDatosEntregaCarteraDao(AppDatabase appDatabase) {
        return appDatabase.datosEntregaCarteraDao();
    }

    @Singleton
    @Provides
    static DestinosCreditoDao provideDestinosCreditoDao(AppDatabase appDatabase) {
        return appDatabase.destinosCreditoDao();
    }

    @Singleton
    @Provides
    static DireccionDao provideDireccionDao(AppDatabase appDatabase) {
        return appDatabase.direccionDao();
    }

    @Singleton
    @Provides
    static DireccionesAutoDao provideDireccionesAutoDao(AppDatabase appDatabase) {
        return appDatabase.direccionesAutoDao();
    }

    @Singleton
    @Provides
    static DireccionesRenDao provideDireccionesRenDao(AppDatabase appDatabase) {
        return appDatabase.direccionesRenDao();
    }

    @Singleton
    @Provides
    static DocumentosDao provideDocumentosDao(AppDatabase appDatabase) {
        return appDatabase.documentosDao();
    }

    @Singleton
    @Provides
    static DocumentosClientesDao provideDocumentosClientesDao(AppDatabase appDatabase) {
        return appDatabase.documentosClientesDao();
    }

    @Singleton
    @Provides
    static DocumentosIntegranteDao provideDocumentosIntegranteDao(AppDatabase appDatabase) {
        return appDatabase.documentosIntegranteDao();
    }

    @Singleton
    @Provides
    static DocumentosIntegranteRenDao provideDocumentosIntegranteRenDao(AppDatabase appDatabase) {
        return appDatabase.documentosIntegranteRenDao();
    }

    @Singleton
    @Provides
    static DocumentosRenDao provideDocumentosRenDao(AppDatabase appDatabase) {
        return appDatabase.documentosRenDao();
    }

    @Singleton
    @Provides
    static DomicilioIntegranteDao provideDomicilioIntegranteDao(AppDatabase appDatabase) {
        return appDatabase.domicilioIntegranteDao();
    }

    @Singleton
    @Provides
    static DomicilioIntegranteAutoDao provideDomicilioIntegranteAutoDao(AppDatabase appDatabase) {
        return appDatabase.domicilioIntegranteAutoDao();
    }

    @Singleton
    @Provides
    static DomicilioIntegranteRenDao provideDomicilioIntegranteRenDao(AppDatabase appDatabase) {
        return appDatabase.domicilioIntegranteRenDao();
    }

    @Singleton
    @Provides
    static EconomicosIndDao provideEconomicosIndDao(AppDatabase appDatabase) {
        return appDatabase.economicosIndDao();
    }

    @Singleton
    @Provides
    static EconomicosIndAutoDao provideEconomicosIndAutoDao(AppDatabase appDatabase) {
        return appDatabase.economicosIndAutoDao();
    }

    @Singleton
    @Provides
    static EconomicosIndRenDao provideEconomicosIndRenDao(AppDatabase appDatabase) {
        return appDatabase.economicosIndRenDao();
    }

    @Singleton
    @Provides
    static EstadosDao provideEstadosDao(AppDatabase appDatabase) {
        return appDatabase.estadosDao();
    }

    @Singleton
    @Provides
    static EstadosCivilesDao provideEstadosCivilesDao(AppDatabase appDatabase) {
        return appDatabase.estadosCivilesDao();
    }

    @Singleton
    @Provides
    static FichasDao provideFichasDao(AppDatabase appDatabase) {
        return appDatabase.fichasDao();
    }

    @Singleton
    @Provides
    static GeoDao provideGeoDao(AppDatabase appDatabase) {
        return appDatabase.geoDao();
    }

    @Singleton
    @Provides
    static GeolocalizacionDao provideGeolocalizacionDao(AppDatabase appDatabase) {
        return appDatabase.geolocalizacionDao();
    }

    @Singleton
    @Provides
    static GestionesVerDomDao provideGestionesVerDomDao(AppDatabase appDatabase) {
        return appDatabase.gestionesVerDomDao();
    }

    @Singleton
    @Provides
    static IdentificacionTipoDao provideIdentificacionTipoDao(AppDatabase appDatabase) {
        return appDatabase.identificacionTipoDao();
    }

    @Singleton
    @Provides
    static ImpresionesVencidaDao provideImpresionesVencidaDao(AppDatabase appDatabase) {
        return appDatabase.impresionesVencidaDao();
    }

    @Singleton
    @Provides
    static ImpresionesVigenteDao provideImpresionesVigenteDao(AppDatabase appDatabase) {
        return appDatabase.impresionesVigenteDao();
    }

    @Singleton
    @Provides
    static IntegrantesGpoDao provideIntegrantesGpoDao(AppDatabase appDatabase) {
        return appDatabase.integrantesGpoDao();
    }

    @Singleton
    @Provides
    static IntegrantesGpoAutoDao provideIntegrantesGpoAutoDao(AppDatabase appDatabase) {
        return appDatabase.integrantesGpoAutoDao();
    }

    @Singleton
    @Provides
    static IntegrantesGpoRenDao provideIntegrantesGpoRenDao(AppDatabase appDatabase) {
        return appDatabase.integrantesGpoRenDao();
    }

    @Singleton
    @Provides
    static LocalidadesDao provideLocalidadesDao(AppDatabase appDatabase) {
        return appDatabase.localidadesDao();
    }

    @Singleton
    @Provides
    static LoginReportDao provideLoginReportDao(AppDatabase appDatabase) {
        return appDatabase.loginReportDao();
    }

    @Singleton
    @Provides
    static MediosContactoDao provideMediosContactoDao(AppDatabase appDatabase) {
        return appDatabase.mediosContactoDao();
    }

    @Singleton
    @Provides
    static MediosPagoOriDao provideMediosPagoOriDao(AppDatabase appDatabase) {
        return appDatabase.mediosPagoOriDao();
    }

    @Singleton
    @Provides
    static MiembrosGpoDao provideMiembrosGpoDao(AppDatabase appDatabase) {
        return appDatabase.miembrosGpoDao();
    }

    @Singleton
    @Provides
    static MiembrosPagosDao provideMiembrosPagosDao(AppDatabase appDatabase) {
        return appDatabase.miembrosPagosDao();
    }

    @Singleton
    @Provides
    static MunicipiosDao provideMunicipiosDao(AppDatabase appDatabase) {
        return appDatabase.municipiosDao();
    }

    @Singleton
    @Provides
    static NegocioIndDao provideNegocioIndDao(AppDatabase appDatabase) {
        return appDatabase.negocioIndDao();
    }

    @Singleton
    @Provides
    static NegocioIndAutoDao provideNegocioIndAutoDao(AppDatabase appDatabase) {
        return appDatabase.negocioIndAutoDao();
    }

    @Singleton
    @Provides
    static NegocioIndRenDao provideNegocioIndRenDao(AppDatabase appDatabase) {
        return appDatabase.negocioIndRenDao();
    }

    @Singleton
    @Provides
    static NegocioIntegranteDao provideNegocioIntegranteDao(AppDatabase appDatabase) {
        return appDatabase.negocioIntegranteDao();
    }

    @Singleton
    @Provides
    static NegocioIntegranteAutoDao provideNegocioIntegranteAutoDao(AppDatabase appDatabase) {
        return appDatabase.negocioIntegranteAutoDao();
    }

    @Singleton
    @Provides
    static NegocioIntegranteRenDao provideNegocioIntegranteRenDao(AppDatabase appDatabase) {
        return appDatabase.negocioIntegranteRenDao();
    }

    @Singleton
    @Provides
    static NivelesEstudiosDao provideNivelesEstudiosDao(AppDatabase appDatabase) {
        return appDatabase.nivelesEstudiosDao();
    }

    @Singleton
    @Provides
    static OcupacionesDao provideOcupacionesDao(AppDatabase appDatabase) {
        return appDatabase.ocupacionesDao();
    }

    @Singleton
    @Provides
    static OtrosDatosIntegranteDao provideOtrosDatosIntegranteDao(AppDatabase appDatabase) {
        return appDatabase.otrosDatosIntegranteDao();
    }

    @Singleton
    @Provides
    static OtrosDatosIntegranteAutoDao provideOtrosDatosIntegranteAutoDao(AppDatabase appDatabase) {
        return appDatabase.otrosDatosIntegranteAutoDao();
    }

    @Singleton
    @Provides
    static OtrosDatosIntegranteRenDao provideOtrosDatosIntegranteRenDao(AppDatabase appDatabase) {
        return appDatabase.otrosDatosIntegranteRenDao();
    }

    @Singleton
    @Provides
    static PagosDao providePagosDao(AppDatabase appDatabase) {
        return appDatabase.pagosDao();
    }

    @Singleton
    @Provides
    static PagosIndDao providePagosIndDao(AppDatabase appDatabase) {
        return appDatabase.pagosIndDao();
    }

    @Singleton
    @Provides
    static ParentescosDao provideParentescosDao(AppDatabase appDatabase) {
        return appDatabase.parentescosDao();
    }

    @Singleton
    @Provides
    static PlazosPrestamosDao providePlazosPrestamosDao(AppDatabase appDatabase) {
        return appDatabase.plazosPrestamosDao();
    }

    @Singleton
    @Provides
    static PoliticasPldIndDao providePoliticasPldIndDao(AppDatabase appDatabase) {
        return appDatabase.politicasPldIndDao();
    }

    @Singleton
    @Provides
    static PoliticasPldIndAutoDao providePoliticasPldIndAutoDao(AppDatabase appDatabase) {
        return appDatabase.politicasPldIndAutoDao();
    }

    @Singleton
    @Provides
    static PoliticasPldIndRenDao providePoliticasPldIndRenDao(AppDatabase appDatabase) {
        return appDatabase.politicasPldIndRenDao();
    }

    @Singleton
    @Provides
    static PoliticasPldIntegranteDao providePoliticasPldIntegranteDao(AppDatabase appDatabase) {
        return appDatabase.politicasPldIntegranteDao();
    }

    @Singleton
    @Provides
    static PoliticasPldIntegranteAutoDao providePoliticasPldIntegranteAutoDao(AppDatabase appDatabase) {
        return appDatabase.politicasPldIntegranteAutoDao();
    }

    @Singleton
    @Provides
    static PoliticasPldIntegranteRenDao providePoliticasPldIntegranteRenDao(AppDatabase appDatabase) {
        return appDatabase.politicasPldIntegranteRenDao();
    }

    @Singleton
    @Provides
    static PrestamosDao providePrestamosDao(AppDatabase appDatabase) {
        return appDatabase.prestamosDao();
    }

    @Singleton
    @Provides
    static PrestamosGpoDao providePrestamosGpoDao(AppDatabase appDatabase) {
        return appDatabase.prestamosGpoDao();
    }

    @Singleton
    @Provides
    static PrestamosIndDao providePrestamosIndDao(AppDatabase appDatabase) {
        return appDatabase.prestamosIndDao();
    }

    @Singleton
    @Provides
    static PrestamosToRenovarDao providePrestamosToRenovarDao(AppDatabase appDatabase) {
        return appDatabase.prestamosToRenovarDao();
    }

    @Singleton
    @Provides
    static RecibosDao provideRecibosDao(AppDatabase appDatabase) {
        return appDatabase.recibosDao();
    }

    @Singleton
    @Provides
    static RecibosAgfCcDao provideRecibosAgfCcDao(AppDatabase appDatabase) {
        return appDatabase.recibosAgfCcDao();
    }

    @Singleton
    @Provides
    static RecibosCcDao provideRecibosCcDao(AppDatabase appDatabase) {
        return appDatabase.recibosCcDao();
    }

    @Singleton
    @Provides
    static RecuperacionRecibosDao provideRecuperacionRecibosDao(AppDatabase appDatabase) {
        return appDatabase.recuperacionRecibosDao();
    }

    @Singleton
    @Provides
    static RecuperacionRecibosCcDao provideRecuperacionRecibosCcDao(AppDatabase appDatabase) {
        return appDatabase.recuperacionRecibosCcDao();
    }

    @Singleton
    @Provides
    static ReferenciaIndDao provideReferenciaIndDao(AppDatabase appDatabase) {
        return appDatabase.referenciaIndDao();
    }

    @Singleton
    @Provides
    static ReferenciaIndAutoDao provideReferenciaIndAutoDao(AppDatabase appDatabase) {
        return appDatabase.referenciaIndAutoDao();
    }

    @Singleton
    @Provides
    static ReferenciaIndRenDao provideReferenciaIndRenDao(AppDatabase appDatabase) {
        return appDatabase.referenciaIndRenDao();
    }

    @Singleton
    @Provides
    static ReimpresionVigenteDao provideReimpresionVigenteDao(AppDatabase appDatabase) {
        return appDatabase.reimpresionVigenteDao();
    }

    @Singleton
    @Provides
    static ReporteSesionesDao provideReporteSesionesDao(AppDatabase appDatabase) {
        return appDatabase.reporteSesionesDao();
    }

    @Singleton
    @Provides
    static RespuestasGpoDao provideRespuestasGpoDao(AppDatabase appDatabase) {
        return appDatabase.respuestasGpoDao();
    }

    @Singleton
    @Provides
    static RespuestasIndDao provideRespuestasIndDao(AppDatabase appDatabase) {
        return appDatabase.respuestasIndDao();
    }

    @Singleton
    @Provides
    static RespuestasIndVDao provideRespuestasIndVDao(AppDatabase appDatabase) {
        return appDatabase.respuestasIndVDao();
    }

    @Singleton
    @Provides
    static RespuestasIntegranteVDao provideRespuestasIntegranteVDao(AppDatabase appDatabase) {
        return appDatabase.respuestasIntegranteVDao();
    }

    @Singleton
    @Provides
    static ResumenesGestionDao provideResumenesGestionDao(AppDatabase appDatabase) {
        return appDatabase.resumenesGestionDao();
    }

    @Singleton
    @Provides
    static SectoresDao provideSectoresDao(AppDatabase appDatabase) {
        return appDatabase.sectoresDao();
    }

    @Singleton
    @Provides
    static ServiciosSincronizadosDao provideServiciosSincronizadosDao(AppDatabase appDatabase) {
        return appDatabase.serviciosSincronizadosDao();
    }

    @Singleton
    @Provides
    static SincronizadoDao provideSincronizadoDao(AppDatabase appDatabase) {
        return appDatabase.sincronizadoDao();
    }

    @Singleton
    @Provides
    static SolicitudesDao provideSolicitudesDao(AppDatabase appDatabase) {
        return appDatabase.solicitudesDao();
    }

    @Singleton
    @Provides
    static SolicitudesAutoDao provideSolicitudesAutoDao(AppDatabase appDatabase) {
        return appDatabase.solicitudesAutoDao();
    }

    @Singleton
    @Provides
    static SolicitudesRenDao provideSolicitudesRenDao(AppDatabase appDatabase) {
        return appDatabase.solicitudesRenDao();
    }

    @Singleton
    @Provides
    static SoporteDao provideSoporteDao(AppDatabase appDatabase) {
        return appDatabase.soporteDao();
    }

    @Singleton
    @Provides
    static SucursalesDao provideSucursalesDao(AppDatabase appDatabase) {
        return appDatabase.sucursalesDao();
    }

    @Singleton
    @Provides
    static SucursalesLocalidadesDao provideSucursalesLocalidadesDao(AppDatabase appDatabase) {
        return appDatabase.sucursalesLocalidadesDao();
    }

    @Singleton
    @Provides
    static TelefonosClienteDao provideTelefonosClienteDao(AppDatabase appDatabase) {
        return appDatabase.telefonosClienteDao();
    }

    @Singleton
    @Provides
    static TelefonosIntegranteDao provideTelefonosIntegranteDao(AppDatabase appDatabase) {
        return appDatabase.telefonosIntegranteDao();
    }

    @Singleton
    @Provides
    static TelefonosIntegranteAutoDao provideTelefonosIntegranteAutoDao(AppDatabase appDatabase) {
        return appDatabase.telefonosIntegranteAutoDao();
    }

    @Singleton
    @Provides
    static TelefonosIntegranteRenDao provideTelefonosIntegranteRenDao(AppDatabase appDatabase) {
        return appDatabase.telefonosIntegranteRenDao();
    }

    @Singleton
    @Provides
    static TicketsDao provideTicketsDao(AppDatabase appDatabase) {
        return appDatabase.ticketsDao();
    }

    @Singleton
    @Provides
    static TrackerAsesorDao provideTrackerAsesorDao(AppDatabase appDatabase) {
        return appDatabase.trackerAsesorDao();
    }

    @Singleton
    @Provides
    static VerificacionesDomiciliariasDao provideVerificacionesDomiciliariasDao(AppDatabase appDatabase) {
        return appDatabase.verificacionesDomiciliariasDao();
    }

    @Singleton
    @Provides
    static ViviendaTiposDao provideViviendaTiposDao(AppDatabase appDatabase) {
        return appDatabase.viviendaTiposDao();
    }

}
