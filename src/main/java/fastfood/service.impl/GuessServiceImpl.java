package fastfood.service.impl;

import fastfood.contant.DBConstant;
import fastfood.contant.Error;
import fastfood.domain.ErrorCustom;
import fastfood.domain.RegisterSalerAccountDTO;
import fastfood.domain.ResponseCommonAPI;
import fastfood.entity.*;
import fastfood.repository.*;
import fastfood.service.GuessService;
import fastfood.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GuessServiceImpl implements GuessService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PurchagePackageRepository purchagePackageRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public ResponseCommonAPI registerSalerAccount(RegisterSalerAccountDTO registerSalerAccountDTO, MultipartFile[] files) throws Exception{
        ResponseCommonAPI res = new ResponseCommonAPI();

        if(files!= null && files.length == 2) {
            if (files[0] != null && !files[0].isEmpty()) {
                String avatarName = fileStorageService.storeFile(files[0]);
                registerSalerAccountDTO.setAvatar(!StringUtils.isEmpty(avatarName) ? avatarName : null);
            }

            if (files[1] != null && !files[1].isEmpty()) {
                String backgroundName = fileStorageService.storeFile(files[1]);
                registerSalerAccountDTO.setBackgroundImage(!StringUtils.isEmpty(backgroundName) ? backgroundName : null);
            }
        }

        UserEntity userEntity = userRepository.findByUsernameAndIsDeletedFalse(registerSalerAccountDTO.getUsername());
        if(userEntity != null ) {
            ErrorCustom errorCustom = new ErrorCustom("username", Error.USER_EXIST.getCode(), Error.USER_EXIST.getMessage());
            res.setSuccess(false);
            res.setError(errorCustom);
            return res;
        }


        // Create userEntity
        UserEntity newUserEntity = registerSalerAccountDTO.toUserEntity();
        newUserEntity.setAvatar(registerSalerAccountDTO.getAvatar());

        List<String> roles = Arrays.asList(DBConstant.ROLE.SALE.getName(), DBConstant.ROLE.USER.getName());
        Set<RoleEntity> listRoles = roleRepository.findByNameInAndIsDeletedFalse(roles);
        Set<UserRoleEntity> listUserRoles = new HashSet<>();
        if(!CollectionUtils.isEmpty(listRoles)) {
            listUserRoles = listRoles.stream().map(t-> {
                UserRoleEntity userRoleEntity = new UserRoleEntity();
                userRoleEntity.setUser(newUserEntity);
                userRoleEntity.setRole(t);
                return  userRoleEntity;
            }).collect(Collectors.toSet());
        }
        newUserEntity.setListUserRoles(listUserRoles);


        // Create supplierEntity
        SupplierEntity supplierEntity = registerSalerAccountDTO.toSupplierEntity();
        supplierEntity.setBackgroundImage(registerSalerAccountDTO.getBackgroundImage());
        supplierEntity.setUser(newUserEntity);

        PurchasePackageEntity purchasePackageEntity = purchagePackageRepository.findByIdAndIsDeletedFalse(registerSalerAccountDTO.getPackage_id());
        if(purchasePackageEntity != null) {
            Calendar fromDate = Calendar.getInstance();
            supplierEntity.setActiveFromDate(fromDate.getTime());
            fromDate.add(Calendar.MONTH, purchasePackageEntity.getPeriod());
            supplierEntity.setActiveToDate(fromDate.getTime());
        }

        // Add category for supplierEntity
        List<CategoryEntity> listCategories = categoryRepository.findByIdInAndIsDeletedFalse(registerSalerAccountDTO.getCategories());
        List<SupplierCategoryEntity> listSupplierCategories = new ArrayList<>();
        if(!CollectionUtils.isEmpty(listCategories)) {
            listSupplierCategories = listCategories.stream().map( t -> {
                SupplierCategoryEntity supplierCategoryEntity = new SupplierCategoryEntity();
                supplierCategoryEntity.setSupplier(supplierEntity);
                supplierCategoryEntity.setCategory(t);
                return supplierCategoryEntity;
            }).collect(Collectors.toList());
        }

        supplierEntity.setListSupplierCategories(listSupplierCategories);

        if(supplierRepository.save(supplierEntity) != null) {
            res.setSuccess(true);
        } else {
            res.setSuccess(false);
        }
        return res;
    }
}
